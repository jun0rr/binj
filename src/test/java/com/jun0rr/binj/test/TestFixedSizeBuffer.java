/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.buffer.BinBuffer;
import java.nio.BufferOverflowException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestFixedSizeBuffer {
  
  @Test
  public void test() {
    BinBuffer buf = BinBuffer.fixedDirectBuffer(64);
    byte[] array = new byte[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = (byte)(Math.random() * Byte.MAX_VALUE * 2);
    }
    buf.put(array, 0, 50);
    System.out.println(buf);
    Assertions.assertThrows(BufferOverflowException.class, ()->buf.put(array, 50, 50));
  }
  
}
