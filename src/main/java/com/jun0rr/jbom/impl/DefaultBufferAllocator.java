/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BufferAllocator;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 *
 * @author Juno
 */
public abstract class DefaultBufferAllocator implements BufferAllocator {
  
  protected final int bufferSize;
  
  protected DefaultBufferAllocator(int bufferSize) {
    this.bufferSize = bufferSize;
  }

  @Override
  public int bufferSize() {
    return bufferSize;
  }
  
  @Override
  public void close() {}

  @Override
  public ByteBuffer alloc() {
    return alloc(bufferSize);
  }

  @Override
  public abstract ByteBuffer alloc(int size);
  
  
  
  public static BufferAllocator directAllocator(int bufferSize) {
    return new DefaultBufferAllocator(bufferSize) {
      public ByteBuffer alloc(int size) {
        return ByteBuffer.allocateDirect(size);
      }
    };
  }
  
  public static BufferAllocator heapAllocator(int bufferSize) {
    return new DefaultBufferAllocator(bufferSize) {
      public ByteBuffer alloc(int size) {
        return ByteBuffer.allocate(size);
      }
    };
  }
  
  public static BufferAllocator mappedFileAllocator(Path root, int bufferSize) {
    final AtomicInteger number = new AtomicInteger(0);
    final Supplier<String> nameSupplier = ()->String.format("jbom.buffer%d.f%d", System.currentTimeMillis(), number.getAndIncrement());
    return mappedFileAllocator(root, nameSupplier, bufferSize);
  }
  
  public static BufferAllocator mappedFileAllocator(Path root, Supplier<String> filename, int bufferSize) {
    return new DefaultBufferAllocator(bufferSize) {
      public ByteBuffer alloc(int size) {
        return ByteBuffer.allocate(size);
      }
    };
  }
  
  
  
  public static class BufferAllocationException extends RuntimeException {
    
    public BufferAllocationException(String msg) {
      super(msg);
    }
    
    public BufferAllocationException(String msg, Object... args) {
      super(String.format(msg, args));
    }
    
    public BufferAllocationException(Throwable cause) {
      super(cause);
    }
    
    public BufferAllocationException(Throwable cause, String msg, Object... args) {
      super(String.format(msg, args), cause);
    }
    
  }
  
}
