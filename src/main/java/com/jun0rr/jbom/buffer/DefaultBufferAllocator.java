/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.buffer;

import java.nio.ByteBuffer;

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
  public ByteBuffer alloc() {
    return alloc(bufferSize);
  }

  @Override
  public abstract ByteBuffer alloc(int size);
  
  
  
  public static class BufferAllocatorException extends RuntimeException {
    
    public BufferAllocatorException(String msg) {
      super(msg);
    }
    
    public BufferAllocatorException(String msg, Object... args) {
      super(String.format(msg, args));
    }
    
    public BufferAllocatorException(Throwable cause) {
      super(cause);
    }
    
    public BufferAllocatorException(Throwable cause, String msg, Object... args) {
      super(String.format(msg, args), cause);
    }
    
  }
  
}
