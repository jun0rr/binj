/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import java.io.Closeable;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author F6036477
 */
public interface BinBuffer extends Closeable {
  
  public int capacity();
  
  public BinBuffer clear();
  
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
  
  public default char getChar() {
    if(remaining() < Character.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Character.BYTES);
    get(b);
    b.flip();
    return b.getChar();
  }

  public default double getDouble() {
    if(remaining() < Double.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Double.BYTES);
    get(b);
    b.flip();
    return b.getDouble();
  }

  public default float getFloat() {
    if(remaining() < Float.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Float.BYTES);
    get(b);
    b.flip();
    return b.getFloat();
  }

  public default int getInt() {
    if(remaining() < Integer.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
    get(b);
    b.flip();
    return b.getInt();
  }

  public default long getLong() {
    if(remaining() < Long.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
    get(b);
    b.flip();
    return b.getLong();
  }

  public default short getShort() {
    if(remaining() < Short.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Short.BYTES);
    get(b);
    b.flip();
    return b.getShort();
  }
  
  public default String getString(Charset cs) {
    ByteBuffer len = ByteBuffer.allocate(Short.BYTES);
    get(len);
    len.flip();
    ByteBuffer bs = ByteBuffer.allocate(len.getShort());
    get(bs);
    bs.flip();
    return cs.decode(bs).toString();
  }
  
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
  
  public default BinBuffer putChar(char s) {
    ByteBuffer b = ByteBuffer.allocate(Character.BYTES);
    b.putChar(s);
    b.flip();
    return put(b);
  }
  
  public default BinBuffer putShort(short s) {
    ByteBuffer b = ByteBuffer.allocate(Short.BYTES);
    b.putShort(s);
    b.flip();
    return put(b);
  }
  
  public default BinBuffer putInt(int i) {
    ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
    b.putInt(i);
    b.flip();
    return put(b);
  }
  
  public default BinBuffer putLong(long l) {
    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
    b.putLong(l);
    b.flip();
    return put(b);
  }
  
  public default BinBuffer putFloat(float f) {
    ByteBuffer b = ByteBuffer.allocate(Float.BYTES);
    b.putFloat(f);
    b.flip();
    return put(b);
  }
  
  public default BinBuffer putDouble(double d) {
    ByteBuffer b = ByteBuffer.allocate(Double.BYTES);
    b.putDouble(d);
    b.flip();
    return put(b);
  }
  
  public default BinBuffer put(String str, Charset cs) {
    ByteBuffer bs = cs.encode(str);
    ByteBuffer len = ByteBuffer.allocate(Short.BYTES);
    len.putShort((short)bs.remaining());
    len.flip();
    put(len);
    put(bs);
    return this;
  }
  
  public default BinBuffer putUTF8(String str) {
    return put(str, StandardCharsets.UTF_8);
  }
  
  public int remaining();
  
  public BinBuffer reset();
  
  public BinBuffer rewind();
  
  public BinBuffer slice();
  
}
