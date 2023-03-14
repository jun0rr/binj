/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.buffer;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author F6036477
 */
public interface BinBuffer extends Closeable {
  
  public BufferAllocator allocator();
  
  public int capacity();
  
  public void capacity(int cap);
  
  public BinBuffer clear();
  
  public List<ByteBuffer> byteBuffers();
  
  @Override
  public default void close() {}
  
  public BinBuffer compact();
  
  public BinBuffer duplicate();
  
  public BinBuffer flip();
  
  public byte get();
  
  public BinBuffer get(byte[] array);
  
  public BinBuffer get(byte[] array, int offset, int length);
  
  public BinBuffer get(ByteBuffer buf);
  
  public BinBuffer get(BinBuffer buf);
  
  public char getChar();

  public double getDouble();

  public float getFloat();

  public int getInt();

  public long getLong();

  public short getShort();
  
  public String getString(Charset cs);
  
  public default String getUTF8() {
    return getString(StandardCharsets.UTF_8);
  }
  
  public boolean hasRemaining();
  
  public int limit();
  
  public BinBuffer limit(int lim);
  
  public BinBuffer mark();
  
  public int position();
  
  public BinBuffer position(int pos);
  
  public BinBuffer put(byte b);
  
  public BinBuffer put(byte[] array);
  
  public BinBuffer put(byte[] array, int offset, int length);
  
  public BinBuffer put(ByteBuffer buf);
  
  public BinBuffer put(BinBuffer buf);
  
  public BinBuffer putChar(char s);
  
  public BinBuffer putShort(short s);
  
  public BinBuffer putInt(int i);
  
  public BinBuffer putLong(long l);
  
  public BinBuffer putFloat(float f);
  
  public BinBuffer putDouble(double d);
  
  public BinBuffer put(String str, Charset cs);
  
  public default BinBuffer putUTF8(String str) {
    return put(str, StandardCharsets.UTF_8);
  }
  
  public int remaining();
  
  public BinBuffer reset();
  
  public BinBuffer rewind();
  
  public BinBuffer slice();
  
  public byte[] hash(String algorithm);
  
  public long checksum();
  
  public String contentString();
  
  
  
  public static BinBuffer of(byte[] bs) {
    return new DefaultBinBuffer(
        BufferAllocator.heapAllocator(bs.length), 
        List.of(ByteBuffer.wrap(bs))
    );
  }
  
  public static BinBuffer of(byte[] bs, int offset, int length) {
    return new DefaultBinBuffer(
        BufferAllocator.heapAllocator(bs.length), 
        List.of(ByteBuffer.wrap(bs, offset, length))
    );
  }
  
  public static BinBuffer of(ByteBuffer buf) {
    return new DefaultBinBuffer(buf.isDirect() 
        ? BufferAllocator.directAllocator(buf.capacity()) 
        : BufferAllocator.heapAllocator(buf.capacity()), 
        List.of(buf)
    );
  }
  
  public static BinBuffer of(BufferAllocator alloc) {
    return new DefaultBinBuffer(alloc);
  }
  
  public static BinBuffer fixedHeapBuffer(int bufsize) {
    return fixedSizeBuffer(ByteBuffer.allocate(bufsize));
  }
  
  public static BinBuffer fixedDirectBuffer(int bufsize) {
    return fixedSizeBuffer(ByteBuffer.allocateDirect(bufsize));
  }
  
  public static BinBuffer fixedSizeBuffer(ByteBuffer buf) {
    return new DefaultBinBuffer(BufferAllocator.overflowAllocator(), List.of(buf));
  }
  
  public static BinBuffer fixedSizeBuffer(byte[] array) {
    return fixedSizeBuffer(ByteBuffer.wrap(array));
  }
  
  public static BinBuffer fixedSizeBuffer(byte[] array, int offset, int length) {
    return fixedSizeBuffer(ByteBuffer.wrap(array, offset, length));
  }
  
  public static BinBuffer ofHeapAllocator(int bufsize) {
    return new DefaultBinBuffer(BufferAllocator.heapAllocator(bufsize));
  }
  
  public static BinBuffer ofDirectAllocator(int bufsize) {
    return new DefaultBinBuffer(BufferAllocator.directAllocator(bufsize));
  }
  
}
