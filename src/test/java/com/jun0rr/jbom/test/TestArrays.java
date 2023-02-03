/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.codec.ByteArrayCodec;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestArrays {
  
  @Test
  public void testByteArray() {
    byte[] array = new byte[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = (byte) (Math.random() * Byte.MAX_VALUE * 2);
    }
    BinCodec<byte[]> c = new ByteArrayCodec();
    System.out.printf("calcSize( byte[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
}
