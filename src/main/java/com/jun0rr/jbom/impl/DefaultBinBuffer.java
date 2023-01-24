/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinBuffer;
import com.jun0rr.jbom.BufferAllocator;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class DefaultBinBuffer implements BinBuffer {
  
  protected final List<ByteBuffer> buffers;
  
  protected final BufferAllocator malloc;
  
  public DefaultBinBuffer(BufferAllocator ba) {
    this.malloc = Objects.requireNonNull(ba);
    this.buffers = new ArrayList<>();
  }
  
  protected DefaultBinBuffer(BufferAllocator ba, List<ByteBuffer> buffers) {
    if(buffers == null || buffers.isEmpty()) {
      throw new IllegalArgumentException("Bad null/empty buffers List");
    }
    this.malloc = Objects.requireNonNull(ba);
    this.buffers = buffers;
  }
  
  @Override
  public int capacity() {
    return buffers.stream().mapToInt(ByteBuffer::capacity).sum();
  }

  @Override
  public BinBuffer clear() {
    buffers.forEach(ByteBuffer::clear);
    return this;
  }

  @Override
  public BinBuffer compact() {
    return this;
  }

  @Override
  public BinBuffer duplicate() {
    return new DefaultBinBuffer(malloc, buffers);
  }
  
  @Override
  public BinBuffer flip() {
    buffers.forEach(ByteBuffer::flip);
    System.out.println("* flip: " + toString());
    return this;
  }
  
  protected void allocate() {
    buffers.add(malloc.alloc());
    System.out.println("* allocate: " + toString());
  }
  
  protected int _index() {
    return position() / malloc.bufferSize();
  }
  
  @Override
  public byte get() {
    if(!hasRemaining()) {
      throw new BufferUnderflowException();
    }
    return buffers.get(_index()).get();
  }

  @Override
  public BinBuffer get(byte[] array) {
    if(remaining() < array.length) {
      throw new BufferUnderflowException();
    }
    int total = 0;
    while(total < array.length) {
      ByteBuffer b = buffers.get(_index());
      int min = Math.min(array.length, b.remaining());
      min = Math.min(min, array.length - total);
      b.get(array, total, min);
      total += min;
    }
    return this;
  }

  @Override
  public BinBuffer get(byte[] array, int offset, int length) {
    if(remaining() < length) {
      throw new BufferUnderflowException();
    }
    int total = 0;
    while(total < length) {
      ByteBuffer b = buffers.get(_index());
      int min = Math.min(length, b.remaining());
      min = Math.min(min, length - total);
      b.get(array, offset + total, min);
      total += min;
    }
    return this;
  }

  @Override
  public BinBuffer get(ByteBuffer buf) {
    while(buf.hasRemaining() && hasRemaining()) {
      ByteBuffer b = buffers.get(_index());
      int lim = b.limit();
      int min = Math.min(buf.remaining(), b.remaining());
      b.limit(b.position() + min);
      buf.put(b);
      b.limit(lim);
    }
    return this;
  }
  
  public BinBuffer get(BinBuffer buf) {
    ((DefaultBinBuffer)buf).buffers.forEach(this::get);
    return this;
  }
  
  @Override
  public boolean hasRemaining() {
    return remaining() > 0;
  }

  @Override
  public int limit() {
    return buffers.stream().mapToInt(ByteBuffer::limit).sum();
  }
  
  @Override
  public BinBuffer limit(int lim) {
    while(lim > capacity()) {
      allocate();
    }
    int i = 0;
    int _lim = lim;
    while(_lim > 0) {
      ByteBuffer b = buffers.get(i++);
      int l = Math.min(b.capacity(), _lim);
      b.limit(l);
      _lim -= l;
    }
    System.out.println("* setLimit: " + this);
    return this;
  }

  @Override
  public BinBuffer mark() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public int position() {
    return buffers.stream().mapToInt(ByteBuffer::position).sum();
  }
  
  @Override
  public BinBuffer position(int pos) {
    while(pos > limit()) {
      limit(pos);
    }
    int i = 0;
    int _pos = pos;
    while(_pos > 0) {
      ByteBuffer b = buffers.get(i++);
      int l = Math.min(b.limit(), _pos);
      b.position(l);
      _pos -= l;
    }
    System.out.println("* setPosition: " + this);
    return this;
  }
  
  @Override
  public BinBuffer put(byte b) {
    if(!hasRemaining()) {
      allocate();
    }
    buffers.get(_index()).put(b);
    return this;
  }

  @Override
  public BinBuffer put(byte[] array) {
    while(remaining() < array.length) {
      allocate();
    }
    int total = 0;
    while(total < array.length) {
      ByteBuffer b = buffers.get(_index());
      int min = Math.min(array.length, b.remaining());
      min = Math.min(min, array.length - total);
      b.put(array, total, min);
      total += min;
    }
    return this;
  }

  @Override
  public BinBuffer put(byte[] array, int offset, int length) {
    while(remaining() < length) {
      allocate();
    }
    int total = 0;
    while(total < length) {
      ByteBuffer b = buffers.get(_index());
      int min = Math.min(length, b.remaining());
      min = Math.min(min, length - total);
      b.put(array, total + offset, min);
      total += min;
    }
    return this;
  }

  @Override
  public BinBuffer put(ByteBuffer buf) {
    while(remaining() < buf.remaining()) {
      allocate();
    }
    int total = 0;
    while(buf.hasRemaining()) {
      ByteBuffer b = buffers.get(_index());
      int lim = buf.limit();
      int min = Math.min(buf.remaining(), b.remaining());
      buf.limit(buf.position() + min);
      b.put(buf);
      total += min;
      buf.limit(lim);
    }
    return this;
  }

  @Override
  public BinBuffer put(BinBuffer buf) {
    ((DefaultBinBuffer)buf).buffers.forEach(this::put);
    return this;
  }
  
  @Override
  public int remaining() {
    return buffers.stream().mapToInt(ByteBuffer::remaining).sum();
  }

  @Override
  public BinBuffer reset() {
    buffers.forEach(ByteBuffer::reset);
    return this;
  }

  @Override
  public BinBuffer rewind() {
    buffers.forEach(ByteBuffer::rewind);
    return this;
  }

  @Override
  public BinBuffer slice() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public String toString() {
    return "DefaultBinBuffer{" + "pos=" + position() + ", lim=" + limit() + ", buffers=" + buffers + '}';
  }
  
  
  
  public static BinBuffer wrap(byte[] bs) {
    return new DefaultBinBuffer(
        DefaultBufferAllocator.heapAllocator(bs.length), 
        List.of(ByteBuffer.wrap(bs))
    );
  }
  
  public static BinBuffer wrap(byte[] bs, int offset, int length) {
    return new DefaultBinBuffer(
        DefaultBufferAllocator.heapAllocator(bs.length), 
        List.of(ByteBuffer.wrap(bs, offset, length))
    );
  }
  
  public static BinBuffer wrap(ByteBuffer buf) {
    return new DefaultBinBuffer(buf.isDirect() 
        ? DefaultBufferAllocator.directAllocator(buf.capacity()) 
        : DefaultBufferAllocator.heapAllocator(buf.capacity()), 
        List.of(buf)
    );
  }
  
}
