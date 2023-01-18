/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.test;

import com.jun0rr.jbon.codec.BooleanCodec;
import com.jun0rr.jbon.codec.ByteCodec;
import com.jun0rr.jbon.codec.CharCodec;
import com.jun0rr.jbon.codec.DoubleCodec;
import com.jun0rr.jbon.codec.FloatCodec;
import com.jun0rr.jbon.codec.InstantCodec;
import com.jun0rr.jbon.codec.IntegerCodec;
import com.jun0rr.jbon.codec.LocalDateCodec;
import com.jun0rr.jbon.codec.LocalDateTimeCodec;
import com.jun0rr.jbon.codec.LongCodec;
import com.jun0rr.jbon.codec.ShortCodec;
import com.jun0rr.jbon.codec.Utf8Codec;
import com.jun0rr.jbon.codec.ZonedDateTimeCodec;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
  
}
