/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 *
 * @author F6036477
 */
public class MappedBufferAllocator extends DefaultBufferAllocator {
  
  private final Path root;
  
  private final AtomicInteger number = new AtomicInteger(0);
  
  private final Supplier<String> filename;
  
  private final List<FileChannel> channels;
  
  private final boolean overwrite;
  
  public MappedBufferAllocator(Path root, Supplier<String> filename, int bufsize, boolean overwrite) {
    super(bufsize);
    this.root = Objects.requireNonNull(root);
    this.filename = Objects.requireNonNull(filename);
    this.channels = new ArrayList<>();
    this.overwrite = overwrite;
    Runtime.getRuntime().addShutdownHook(new Thread(this::close));
  }
  
  public MappedBufferAllocator(Path root, int bufsize, boolean overwrite) {
    super(bufsize);
    this.root = Objects.requireNonNull(root);
    this.filename = ()->String.format("binj.buffer@%d.f%d", MappedBufferAllocator.this.hashCode(), number.getAndIncrement());
    this.channels = new ArrayList<>();
    this.overwrite = overwrite;
    Runtime.getRuntime().addShutdownHook(new Thread(this::close));
  }
  
  public MappedBufferAllocator(Path root, int bufsize) {
    this(root, bufsize, false);
  }
  
  private void close(FileChannel c) {
    try {
      c.close();
    }
    catch(IOException e) {
      throw new DefaultBufferAllocator.BufferAllocatorException(e);
    }
  }
  
  @Override
  public void close() {
    channels.stream().filter(FileChannel::isOpen).forEach(this::close);
    channels.clear();
  }
  
  @Override
  public ByteBuffer alloc(int size) {
    try {
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
      FileChannel c = FileChannel.open(root.resolve(filename.get()), opts);
      channels.add(c);
      return c.map(FileChannel.MapMode.READ_WRITE, 0, size);
    }
    catch(IOException e) {
      throw new DefaultBufferAllocator.BufferAllocatorException(e);
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + Objects.hashCode(this.root);
    hash = 61 * hash + Objects.hashCode(this.number);
    hash = 61 * hash + Objects.hashCode(this.filename);
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
    if (!Objects.equals(this.root, other.root)) {
      return false;
    }
    if (!Objects.equals(this.number, other.number)) {
      return false;
    }
    return Objects.equals(this.filename, other.filename);
  }
  
  @Override
  public String toString() {
    return "MappedBufferAllocator{" + "root=" + root + ", filename=" + filename + ", channels=" + channels + '}';
  }
  
}
