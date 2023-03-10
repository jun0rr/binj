/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.buffer;

import java.io.Closeable;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public interface BufferAllocator extends Closeable {
  
  public int bufferSize();
  
  public ByteBuffer alloc();
  
  @Override public default void close() {}
  
  
  
  public static class DirectAllocator extends DefaultBufferAllocator {
    
    public DirectAllocator(int bufsize) {
      super(bufsize);
    }
    
    @Override
    public ByteBuffer alloc() {
      return ByteBuffer.allocateDirect(bufferSize());
    }
    
  }
  
  public static class HeapAllocator extends DefaultBufferAllocator {
    
    public HeapAllocator(int bufsize) {
      super(bufsize);
    }
    
    @Override
    public ByteBuffer alloc() {
      return ByteBuffer.allocate(bufferSize());
    }
    
  }
  
  public class OverflowBufferAllocator implements BufferAllocator {
    
    @Override
    public int bufferSize() {
      return 0;
    }

    @Override
    public ByteBuffer alloc() {
      throw new BufferOverflowException();
    }

  }

  
  
  
  public static BufferAllocator directAllocator(int bufferSize) {
    return new DirectAllocator(bufferSize);
  }
  
  public static BufferAllocator heapAllocator(int bufferSize) {
    return new HeapAllocator(bufferSize);
  }
  
  public static BufferAllocator overflowAllocator() {
    return new OverflowBufferAllocator();
  }
  
}
