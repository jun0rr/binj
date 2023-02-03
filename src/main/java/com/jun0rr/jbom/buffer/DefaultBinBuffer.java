/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.buffer;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

/**
 *
 * @author Juno
 */
public class DefaultBinBuffer implements BinBuffer {
  
  protected final List<ByteBuffer> buffers;
  
  protected final BufferAllocator malloc;
  
  protected int mark;
  
  public DefaultBinBuffer(BufferAllocator ba) {
    this.malloc = Objects.requireNonNull(ba);
    this.buffers = new ArrayList<>();
  }
  
  public DefaultBinBuffer(BufferAllocator ba, List<ByteBuffer> buffers) {
    if(buffers == null || buffers.isEmpty()) {
      throw new IllegalArgumentException("Bad null/empty buffers List");
    }
    this.malloc = Objects.requireNonNull(ba);
    this.buffers = buffers;
  }
  
  @Override
  public BufferAllocator allocator() {
    return malloc;
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
    List<ByteBuffer> ls = buffers.stream().collect(Collectors.toList());
    buffers.clear();
    ls.stream()
        .map(b->malloc.alloc())
        .forEach(buffers::add);
    ls.stream()
        .filter(ByteBuffer::hasRemaining)
        .forEach(b->put(b));
    ls.clear();
    return this;
  }

  @Override
  public BinBuffer duplicate() {
    return new DefaultBinBuffer(malloc, buffers);
  }
  
  @Override
  public BinBuffer flip() {
    buffers.forEach(ByteBuffer::flip);
    return this;
  }
  
  protected void allocate() {
    buffers.add(malloc.alloc());
  }
  
  protected int _index() {
    for(int i = 0; i < buffers.size(); i++) {
      if(buffers.get(i).hasRemaining()) {
        return i;
      }
    }
    return buffers.size() -1;
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
  public char getChar() {
    if(remaining() < Character.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < Character.BYTES) {
      b = ByteBuffer.allocate(Character.BYTES);
      get(b);
      b.flip();
    }
    return b.getChar();
  }

  @Override
  public double getDouble() {
    if(remaining() < Double.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < Double.BYTES) {
      b = ByteBuffer.allocate(Double.BYTES);
      get(b);
      b.flip();
    }
    return b.getDouble();
  }

  @Override
  public float getFloat() {
    if(remaining() < Float.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < Float.BYTES) {
      b = ByteBuffer.allocate(Float.BYTES);
      get(b);
      b.flip();
    }
    return b.getFloat();
  }

  @Override
  public int getInt() {
    if(remaining() < Integer.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < Integer.BYTES) {
      b = ByteBuffer.allocate(Integer.BYTES);
      get(b);
      b.flip();
    }
    return b.getInt();
  }

  @Override
  public long getLong() {
    if(remaining() < Long.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < Long.BYTES) {
      b = ByteBuffer.allocate(Long.BYTES);
      get(b);
      b.flip();
    }
    return b.getLong();
  }

  @Override
  public short getShort() {
    if(remaining() < Short.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < Short.BYTES) {
      b = ByteBuffer.allocate(Short.BYTES);
      get(b);
      b.flip();
    }
    return b.getShort();
  }
  
  @Override
  public String getString(Charset cs) {
    int len = getShort();
    if(remaining() < len) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() < len) {
      b = ByteBuffer.allocate(len);
      get(b);
      b.flip();
    }
    return cs.decode(b).toString();
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
  
  @Override
  public BinBuffer get(BinBuffer buf) {
    if(!hasRemaining()) {
      throw new BufferUnderflowException();
    }
    buffers.stream()
        .filter(ByteBuffer::hasRemaining)
        .forEach(buf::put);
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
    while(_lim > 0 && i < buffers.size()) {
      ByteBuffer b = buffers.get(i++);
      int l = Math.min(b.capacity(), _lim);
      b.limit(l);
      _lim -= l;
    }
    return this;
  }

  @Override
  public BinBuffer mark() {
    this.mark = position();
    return this;
  }

  @Override
  public int position() {
    return buffers.stream().mapToInt(ByteBuffer::position).sum();
  }
  
  @Override
  public BinBuffer position(int pos) {
    while(pos > capacity()) {
      allocate();
    }
    int i = 0;
    int _pos = pos;
    while(_pos >= 0 && i < buffers.size()) {
      ByteBuffer b = buffers.get(i++);
      int l = Math.min(b.limit(), _pos);
      b.position(l);
      _pos -= l;
    }
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
  public BinBuffer putChar(char s) {
    while(remaining() < Character.BYTES) {
      allocate();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() >= Character.BYTES) {
      b.putChar(s);
    }
    else {
      b = ByteBuffer.allocate(Character.BYTES);
      b.putChar(s);
      b.flip();
      put(b);
    }
    return this;
  }
  
  @Override
  public BinBuffer putShort(short s) {
    while(remaining() < Short.BYTES) {
      allocate();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() >= Short.BYTES) {
      b.putShort(s);
    }
    else {
      b = ByteBuffer.allocate(Short.BYTES);
      b.putShort(s);
      b.flip();
      put(b);
    }
    return this;
  }
  
  @Override
  public BinBuffer putInt(int s) {
    while(remaining() < Integer.BYTES) {
      allocate();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() >= Integer.BYTES) {
      b.putInt(s);
    }
    else {
      b = ByteBuffer.allocate(Integer.BYTES);
      b.putInt(s);
      b.flip();
      put(b);
    }
    return this;
  }
  
  @Override
  public BinBuffer putLong(long s) {
    while(remaining() < Long.BYTES) {
      allocate();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() >= Long.BYTES) {
      b.putLong(s);
    }
    else {
      b = ByteBuffer.allocate(Long.BYTES);
      b.putLong(s);
      b.flip();
      put(b);
    }
    return this;
  }
  
  @Override
  public BinBuffer putFloat(float s) {
    while(remaining() < Float.BYTES) {
      allocate();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() >= Float.BYTES) {
      b.putFloat(s);
    }
    else {
      b = ByteBuffer.allocate(Float.BYTES);
      b.putFloat(s);
      b.flip();
      put(b);
    }
    return this;
  }
  
  @Override
  public BinBuffer putDouble(double s) {
    while(remaining() < Double.BYTES) {
      allocate();
    }
    ByteBuffer b = buffers.get(_index());
    if(b.remaining() >= Double.BYTES) {
      b.putDouble(s);
    }
    else {
      b = ByteBuffer.allocate(Double.BYTES);
      b.putDouble(s);
      b.flip();
      put(b);
    }
    return this;
  }
  
  @Override
  public BinBuffer put(String str, Charset cs) {
    ByteBuffer bs = cs.encode(str);
    putShort((short)bs.remaining());
    put(bs);
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
    buf.get(this);
    return this;
  }
  
  @Override
  public int remaining() {
    return buffers.stream().mapToInt(ByteBuffer::remaining).sum();
  }

  @Override
  public BinBuffer reset() {
    position(mark);
    return this;
  }

  @Override
  public BinBuffer rewind() {
    position(0);
    mark = 0;
    return this;
  }

  @Override
  public BinBuffer slice() {
    return new DefaultBinBuffer(malloc, buffers.stream()
        .filter(ByteBuffer::hasRemaining)
        .map(ByteBuffer::slice)
        .collect(Collectors.toList())
    );
  }
  
  @Override
  public byte[] hash(String algorithm) {
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      buffers.stream()
          .filter(ByteBuffer::hasRemaining)
          .forEach(md::update);
      return md.digest();
    }
    catch(NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public long checksum() {
    CRC32 crc = new CRC32();
    buffers.stream()
        .filter(ByteBuffer::hasRemaining)
        .forEach(crc::update);
    return crc.getValue();
  }

  @Override
  public String toString() {
    return "DefaultBinBuffer{" + "pos=" + position() + ", lim=" + limit() + ", buffers=" + buffers.size() + '}';
  }
  
}
