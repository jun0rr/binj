/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinCodec;
import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.codec.ArrayCodec;
import com.jun0rr.binj.codec.BooleanArrayCodec;
import com.jun0rr.binj.codec.ByteArrayCodec;
import com.jun0rr.binj.codec.CharArrayCodec;
import com.jun0rr.binj.codec.DoubleArrayCodec;
import com.jun0rr.binj.codec.FloatArrayCodec;
import com.jun0rr.binj.codec.IntArrayCodec;
import com.jun0rr.binj.codec.LongArrayCodec;
import com.jun0rr.binj.codec.ShortArrayCodec;
import com.jun0rr.binj.impl.DefaultBinType;
import com.jun0rr.binj.mapping.FieldGetterStrategy;
import com.jun0rr.binj.mapping.FieldsOrderConstructStrategy;
import java.time.LocalDate;
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
  
  @Test
  public void testBooleanArray() {
    boolean[] array = new boolean[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = Math.round(Math.random()) == 1;
    }
    BinCodec<boolean[]> c = new BooleanArrayCodec();
    System.out.printf("calcSize( boolean[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test
  public void testCharArray() {
    char[] array = new char[100];
    for(int i = 64; i < array.length; i++) {
      array[i-64] = (char) i;
    }
    BinCodec<char[]> c = new CharArrayCodec();
    System.out.printf("calcSize( char[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test
  public void testDoubleArray() {
    double[] array = new double[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = Math.random() * Double.MAX_VALUE;
    }
    BinCodec<double[]> c = new DoubleArrayCodec();
    System.out.printf("calcSize( double[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test
  public void testFloatArray() {
    float[] array = new float[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = (float)(Math.random() * Float.MAX_VALUE);
    }
    BinCodec<float[]> c = new FloatArrayCodec();
    System.out.printf("calcSize( float[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test
  public void testIntArray() {
    int[] array = new int[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = (int)(Math.random() * Integer.MAX_VALUE);
    }
    BinCodec<int[]> c = new IntArrayCodec();
    System.out.printf("calcSize( int[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test
  public void testLongArray() {
    long[] array = new long[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = (long)(Math.random() * Long.MAX_VALUE);
    }
    BinCodec<long[]> c = new LongArrayCodec();
    System.out.printf("calcSize( long[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test
  public void testShortArray() {
    short[] array = new short[100];
    for(int i = 0; i < array.length; i++) {
      array[i] = (short)(Math.random() * Short.MAX_VALUE);
    }
    BinCodec<short[]> c = new ShortArrayCodec();
    System.out.printf("calcSize( short[] ): %d - %s%n", c.calcSize(array), Arrays.toString(array));
    BinBuffer buf = BinBuffer.ofDirectAllocator(32);
    c.write(buf, array);
    buf.flip();
    System.out.println(buf);
    Assertions.assertArrayEquals(array, c.read(buf));
  }
  
  @Test public void testPersonArray() {
    Person[] pss = new Person[100];
    for(int i = 0; i < pss.length; i++) {
      pss[i] = new Person("Hello-" + i, "World-" + i, LocalDate.now(), new Address("Street-" + i, "City-" + i, i + 101));
    }
    BinContext ctx = BinContext.newContext();
    ctx.mapper().constructStrategies().add(new FieldsOrderConstructStrategy());
    ctx.mapper().extractStrategies().add(new FieldGetterStrategy());
    BinBuffer buf = BinBuffer.ofDirectAllocator(512);
    BinCodec<Person[]> codec = new ArrayCodec(ctx, new DefaultBinType(Person[].class));
    System.out.printf("calcSize( Person[] ): %d - %s%n", codec.calcSize(pss), Arrays.toString(pss));
    codec.write(buf, pss);
    System.out.printf("buf=%s%n", buf);
    pss = codec.read(buf.flip());
    System.out.printf("codec.read( %s ): %s%n", buf, Arrays.toString(pss));
  }
  
  public static record Person(String name, String last, LocalDate birth, Address address){}
  
  public static record Address(String street, String city, int number) {}
  
}
