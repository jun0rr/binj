/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.codec.BooleanCodec;
import com.jun0rr.binj.codec.ByteCodec;
import com.jun0rr.binj.codec.CharCodec;
import com.jun0rr.binj.codec.DoubleCodec;
import com.jun0rr.binj.codec.FloatCodec;
import com.jun0rr.binj.codec.InstantCodec;
import com.jun0rr.binj.codec.IntegerCodec;
import com.jun0rr.binj.codec.LocalDateCodec;
import com.jun0rr.binj.codec.LocalDateTimeCodec;
import com.jun0rr.binj.codec.LongCodec;
import com.jun0rr.binj.codec.ShortCodec;
import com.jun0rr.binj.codec.Utf8Codec;
import com.jun0rr.binj.codec.ZonedDateTimeCodec;
import com.jun0rr.binj.impl.DefaultBinContext;
import com.jun0rr.binj.impl.IndexedKey;
import com.jun0rr.binj.mapping.ObjectMapper;
import com.jun0rr.binj.type.BinMap;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestBinCodecs {
  
  @Test
  public void booleanCodec() {
    Boolean b = Boolean.TRUE;
    BooleanCodec codec = new BooleanCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void byteCodec() {
    Byte b = (byte) 0x7c;
    ByteCodec codec = new ByteCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void charCodec() {
    Character b = '{';
    CharCodec codec = new CharCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void doubleCodec() {
    Double b = Math.random() * 1000;
    DoubleCodec codec = new DoubleCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void floatCodec() {
    Float b = (float)(Math.random() * 1000);
    FloatCodec codec = new FloatCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void integerCodec() {
    Integer b = (int)(Math.random() * 1000);
    IntegerCodec codec = new IntegerCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void localDateCodec() {
    LocalDate b = LocalDate.now();
    LocalDateCodec codec = new LocalDateCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void localDateTimeCodec() {
    LocalDateTime b = LocalDateTime.ofInstant(Instant.ofEpochMilli(Instant.now().toEpochMilli()), ZoneOffset.UTC);
    LocalDateTimeCodec codec = new LocalDateTimeCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void zonedDateTimeCodec() {
    ZonedDateTime b = Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneOffset.UTC);
    ZonedDateTimeCodec codec = new ZonedDateTimeCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void instantCodec() {
    Instant b = Instant.ofEpochMilli(Instant.now().toEpochMilli());
    InstantCodec codec = new InstantCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void longCodec() {
    Long b = (long)(Math.random() * 1000);
    LongCodec codec = new LongCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void shortCodec() {
    Short b = (short)(Math.random() * 1000);
    ShortCodec codec = new ShortCodec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void utf8Codec() {
    String b = "Hello World 123456789ABC!!!";
    Utf8Codec codec = new Utf8Codec();
    ByteBuffer buf = ByteBuffer.allocate(codec.calcSize(b));
    codec.write(buf, b);
    buf.flip();
    Assertions.assertEquals(b, codec.read(buf));
  }
  
  @Test
  public void indexedKeyCodec() {
    IndexedKey<String> i = new IndexedKey<>(1, "Hello World 123456789ABC!!!");
    BinContext ctx = new DefaultBinContext(new ObjectMapper());
    ByteBuffer buf = ByteBuffer.allocate(ctx.calcSize(i));
    ctx.write(buf, i);
    buf.flip();
    Assertions.assertEquals(i, ctx.read(buf));
  }
  
  @Test
  public void collectionCodec() {
    Collection<String> cs = new ArrayList<>();
    char c = 0x41;
    for(int i = 0; i < 31; i++) {
      cs.add(Character.toString(c++));
    }
    BinContext ctx = new DefaultBinContext(new ObjectMapper());
    ByteBuffer buf = ByteBuffer.allocate(ctx.calcSize(cs));
    ctx.write(buf, cs);
    buf.flip();
    Assertions.assertEquals(cs, ctx.read(buf));
  }
  
  @Test
  public void arrayCodec() {
    String[] cs = new String[31];
    char c = 0x41;
    for(int i = 0; i < 31; i++) {
      cs[i] = Character.toString(c++);
    }
    BinContext ctx = BinContext.ofCombinedStrategyMapper();
    BinBuffer buf = BinBuffer.ofHeapAllocator(ctx.calcSize(cs));
    ctx.write(buf, cs);
    buf.flip();
    String[] ds = ctx.read(buf);
    System.out.printf("* TestBinCodecs.arrayCodec: array1=%s%n", Arrays.toString(cs));
    System.out.printf("* TestBinCodecs.arrayCodec: array2=%s%n", Arrays.toString(ds));
    Assertions.assertTrue(Arrays.equals(cs, ds));
  }
  
  @Test
  public void mapCodec() {
    Map<Integer,String> map = new HashMap<>();
    char c = 0x41;
    for(int i = 0; i < 31; i++) {
      map.put((int)c, Character.toString(c++));
    }
    BinContext ctx = new DefaultBinContext(new ObjectMapper());
    ByteBuffer buf = ByteBuffer.allocate(ctx.calcSize(map));
    ctx.write(buf, map);
    buf.flip();
    Assertions.assertEquals(map, ctx.read(buf));
  }
  
  @Test
  public void binMap() {
    Map<Integer,String> map = new HashMap<>();
    char c = 0x41;
    for(int i = 0; i < 31; i++) {
      map.put((int)c, Character.toString(c++));
    }
    BinContext ctx = new DefaultBinContext(new ObjectMapper());
    ByteBuffer buf = ByteBuffer.allocate(ctx.calcSize(map));
    ctx.write(buf, map);
    buf.flip();
    BinMap<Integer,String> bm = new BinMap(ctx, buf);
  }
  
}
