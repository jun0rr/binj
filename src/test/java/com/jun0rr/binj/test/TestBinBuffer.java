/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.buffer.BinBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Juno
 */
public class TestBinBuffer {
  
  @Test
  public void put_byte() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    buf.limit(27);
    byte[] bs = new byte[27];
    byte c = 0x41;
    for(int i = 0; i < bs.length; i++) {
      bs[i] = c;
      buf.put(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(bs[i++], buf.get());
    }
  }
  
  @Test
  public void position_10_limit_25() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    buf.limit(27);
    byte[] bs = new byte[27];
    byte c = 0x41;
    for(int i = 0; i < bs.length; i++) {
      bs[i] = c;
      buf.put(c++);
    }
    buf.flip();
    buf.position(10).limit(25);
    int i = 10;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(bs[i++], buf.get());
    }
  }
  
  @Test
  public void put_bytearray() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    byte[] array = new byte[27];
    byte c = 0x41;
    for(int i = 0; i < 27; i++) {
      array[i] = c++;
    }
    buf.put(array);
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(array[i++], buf.get());
    }
  }
  
  @Test
  public void put_bytearray_offset_length() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    byte[] array = new byte[27];
    byte c = 0x41;
    for(int i = 0; i < 27; i++) {
      array[i] = c++;
    }
    buf.put(array, 0, 12);
    buf.put(array, 12, 15);
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(array[i++], buf.get());
    }
  }
  
  @Test
  public void put_char() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    char[] cs = new char[27];
    char c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putChar(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getChar());
    }
  }
  
  @Test
  public void put_short() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    short[] cs = new short[27];
    short c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putShort(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getShort());
    }
  }
  
  @Test
  public void put_int() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    int[] cs = new int[27];
    int c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putInt(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getInt());
    }
  }
  
  @Test
  public void put_long() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    long[] cs = new long[27];
    long c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putLong(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getLong());
    }
  }
  
  @Test
  public void put_float() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    float[] cs = new float[27];
    float c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putFloat(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getFloat());
    }
  }
  
  @Test
  public void put_double() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    double[] cs = new double[27];
    double c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putDouble(c++);
    }
    buf.flip();
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getDouble());
    }
  }
  
  @Test
  public void compact() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    buf.position(24);
    char[] cs = new char[27];
    char c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putChar(c++);
    }
    buf.flip();
    buf.position(24);
    buf.compact();
    buf.flip();
    Assertions.assertEquals(0, buf.position());
    Assertions.assertEquals(54, buf.limit());
    int i = 0;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getChar());
    }
  }
  
  @Test
  public void slice() {
    BinBuffer buf = BinBuffer.ofHeapAllocator(10);
    char[] cs = new char[27];
    char c = 0x41;
    for(int i = 0; i < cs.length; i++) {
      cs[i] = c;
      buf.putChar(c++);
    }
    buf.flip();
    buf.position(14);
    buf = buf.slice();
    Assertions.assertEquals(0, buf.position());
    int i = 7;
    while(buf.hasRemaining()) {
      Assertions.assertEquals(cs[i++], buf.getChar());
    }
  }
  
  @Test public void checksum() {
    //System.out.printf("------ chechsum ------%n");
    BinBuffer buf = BinBuffer.ofDirectAllocator(128);
    //System.out.printf("BinBuffer(rem=%d): %s%n", buf.remaining(), buf);
    for(int i = 0; i < 100; i++) {
      buf.putInt(i);
    }
    Assertions.assertEquals(400, buf.position());
    buf.flip();
    Assertions.assertEquals(400, buf.remaining());
    //System.out.printf("BinBuffer(rem=%d): %s%n", buf.remaining(), buf);
    //System.out.printf("BinBuffer(rem=%d): %s%n", buf.remaining(), buf);
    long sum = buf.checksum();
    Assertions.assertEquals(2738611575L, sum);
    Assertions.assertEquals(0, buf.remaining());
  }
  
}
