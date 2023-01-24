/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinBuffer;
import com.jun0rr.jbom.impl.DefaultBufferAllocator;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Juno
 */
public class TestBinBuffer {
  
  @Test
  public void put_byte() {
    System.out.println("--------- put_byte() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    buf.limit(27);
    byte c = 0x41;
    for(int i = 0; i < 27; i++) {
      buf.put(c++);
    }
    buf.flip();
    System.out.printf("[");
    while(buf.hasRemaining()) {
      System.out.printf("%d, ", buf.get());
    }
    System.out.println("]");
  }
  
  @Test
  public void position_10_limit_25() {
    System.out.println("--------- position_10_limit_25() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    buf.limit(27);
    byte c = 0x41;
    for(int i = 0; i < 27; i++) {
      buf.put(c++);
    }
    buf.flip();
    buf.position(10).limit(25);
    System.out.println(buf);
    System.out.printf("[");
    while(buf.hasRemaining()) {
      System.out.printf("%d, ", buf.get());
    }
    System.out.println("]");
  }
  
  @Test
  public void put_bytearray() {
    System.out.println("--------- put_bytearray() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    byte[] array = new byte[27];
    byte c = 0x41;
    for(int i = 0; i < 27; i++) {
      array[i] = c++;
    }
    buf.put(array);
    System.out.println(buf);
    buf.flip();
    System.out.printf("[");
    while(buf.hasRemaining()) {
      System.out.printf("%d, ", buf.get());
    }
    System.out.println("]");
  }
  
  @Test
  public void put_bytearray_offset_length() {
    System.out.println("--------- put_bytearray_offset_length() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    byte[] array = new byte[27];
    byte c = 0x41;
    for(int i = 0; i < 27; i++) {
      array[i] = c++;
    }
    buf.put(array, 0, 12);
    System.out.println(buf);
    buf.put(array, 12, 15);
    System.out.println(buf);
    buf.flip();
    System.out.printf("[");
    while(buf.hasRemaining()) {
      System.out.printf("%d, ", buf.get());
    }
    System.out.println("]");
  }
  
  @Test
  public void put_char() {
    System.out.println("--------- put_char() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      char c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putChar(c++);
      }
      buf.flip();
      System.out.println(buf);
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getChar());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void put_short() {
    System.out.println("--------- put_short() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      short c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putShort(c++);
      }
      buf.flip();
      System.out.println(buf);
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getShort());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void put_int() {
    System.out.println("--------- put_int() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      int c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putInt(c++);
      }
      buf.flip();
      System.out.println(buf);
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getInt());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void put_long() {
    System.out.println("--------- put_long() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      long c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putLong(c++);
      }
      buf.flip();
      System.out.println(buf);
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getLong());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void put_float() {
    System.out.println("--------- put_float() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      float c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putFloat(c++);
      }
      buf.flip();
      System.out.println(buf);
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getFloat());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void put_double() {
    System.out.println("--------- put_double() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      double c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putDouble(c++);
      }
      buf.flip();
      System.out.println(buf);
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getDouble());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void compact() {
    System.out.println("--------- compact() ---------");
    BinBuffer buf = new DefaultBinBuffer(DefaultBufferAllocator.heapAllocator(10));
    try {
      buf.position(24);
      System.out.println("position(24): " + buf);
      char c = 0x41;
      for(int i = 0; i < 27; i++) {
        buf.putChar(c++);
      }
      buf.flip();
      System.out.println("flip        : " + buf);
      buf.position(24);
      System.out.println("position(24): " + buf);
      buf.compact();
      System.out.println("compact     : " + buf);
      buf.flip();
      System.out.printf("[");
      while(buf.hasRemaining()) {
        System.out.printf("%s, ", buf.getChar());
      }
      System.out.println("]");
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
