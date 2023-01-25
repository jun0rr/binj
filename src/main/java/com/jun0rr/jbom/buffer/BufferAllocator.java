/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom.buffer;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 *
 * @author F6036477
 */
public interface BufferAllocator extends Closeable {
  
  public int bufferSize();
  
  public ByteBuffer alloc();
  
  public ByteBuffer alloc(int size);
  
  @Override
  public default void close() {}
  
  
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
    return new MappedBufferAllocator(root, bufferSize);
  }
  
  public static BufferAllocator mappedFileAllocator(Path root, int bufferSize, boolean overwrite) {
    return new MappedBufferAllocator(root, bufferSize, overwrite);
  }
  
  public static BufferAllocator mappedFileAllocator(Path root, Supplier<String> filename, int bufferSize, boolean overwrite) {
    return new MappedBufferAllocator(root, filename, bufferSize, overwrite);
  }
  
}
