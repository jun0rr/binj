/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.buffer;

import com.jun0rr.binj.impl.Rethrow;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class MappedBufferAllocator extends DefaultBufferAllocator {
  
  private final PathSupplier filesup;
  
  private final AtomicLong offset;
  
  private final List<FileChannel> channels;
  
  private final boolean overwrite;
  
  public MappedBufferAllocator(PathSupplier sup, int bufsize, boolean overwrite) {
    super(bufsize);
    this.filesup = Objects.requireNonNull(sup);
    this.channels = new CopyOnWriteArrayList<>(filesup.existing()
        .map(p->Rethrow.run(()->FileChannel.open(p, openOptions())))
        .collect(Collectors.toList())
    );
    this.offset = new AtomicLong(0);
    this.overwrite = overwrite;
    Runtime.getRuntime().addShutdownHook(new Thread(this::close));
  }
  
  public MappedBufferAllocator(PathSupplier sup, int bufsize) {
    this(sup, bufsize, false);
  }
  
  
  public List<FileChannel> channels() {
    return channels;
  }
  
  public List<ByteBuffer> readBuffers() {
    if(channels.isEmpty()) return Collections.EMPTY_LIST;
    try {
      List<ByteBuffer> bufs = new LinkedList<>();
      for(FileChannel c : channels) {
        long size = c.size();
        long len = 0;
        while(len < size) {
          bufs.add(c.map(FileChannel.MapMode.READ_WRITE, len, bufferSize()));
          len += bufferSize();
        }
        offset.set(len);
        //System.out.printf("MappedBufferAllocator.readBuffers(): len=%d, size=%d, bufs=%d, buf=%s%n", len, size, bufs.size(), bufs.get(0));
      }
      return bufs;
    }
    catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public void close() {
    channels.stream()
        .filter(FileChannel::isOpen)
        .forEach(Rethrow.consumer(d->d.close()));
    channels.clear();
  }
  
  private OpenOption[] openOptions() {
    OpenOption[] opts;
    if(overwrite) {
      opts = new OpenOption[]{
        StandardOpenOption.CREATE, 
        StandardOpenOption.TRUNCATE_EXISTING, 
        StandardOpenOption.READ, 
        StandardOpenOption.WRITE, 
        StandardOpenOption.DSYNC
      };
    }
    else {
      opts = new OpenOption[]{
        StandardOpenOption.CREATE, 
        StandardOpenOption.READ, 
        StandardOpenOption.WRITE, 
        StandardOpenOption.DSYNC
      };
    }
    return opts;
  }
  
  @Override
  public synchronized ByteBuffer alloc(int size) {
    try {
      if(!channels.isEmpty()) {
        FileChannel c = channels.get(channels.size()-1);
        if(c.size() < Integer.MAX_VALUE) {
          //System.out.printf("MappedBufferAllocator.alloc(%d): channel.size=%d, offset=%s%n", size, c.size(), offset);
          return c.map(FileChannel.MapMode.READ_WRITE, offset.getAndAdd(size), size);
        }
      }
      FileChannel c = FileChannel.open(filesup.get(), openOptions());
      channels.add(c);
      return c.map(FileChannel.MapMode.READ_WRITE, 0, size);
    }
    catch(IOException e) {
      throw new DefaultBufferAllocator.BufferAllocatorException(e);
    }
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 23 * hash + Objects.hashCode(this.filesup);
    hash = 23 * hash + Objects.hashCode(this.offset);
    hash = 23 * hash + Objects.hashCode(this.channels);
    hash = 23 * hash + (this.overwrite ? 1 : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MappedBufferAllocator other = (MappedBufferAllocator) obj;
    if (this.overwrite != other.overwrite) {
      return false;
    }
    if (!Objects.equals(this.filesup, other.filesup)) {
      return false;
    }
    if (!Objects.equals(this.offset, other.offset)) {
      return false;
    }
    return Objects.equals(this.channels, other.channels);
  }

  @Override
  public String toString() {
    return "MappedBufferAllocator{" + "filesup=" + filesup + ", offset=" + offset + ", channels=" + channels + ", overwrite=" + overwrite + '}';
  }

}
