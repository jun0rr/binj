/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.impl;

import com.jun0rr.jbon.BinCodec;
import com.jun0rr.jbon.BinContext;
import com.jun0rr.jbon.BinType;
import com.jun0rr.jbon.BinTypeNotFoundException;
import com.jun0rr.jbon.UnexpectedBinTypeException;
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
import com.jun0rr.jbon.codec.MapCodec;
import com.jun0rr.jbon.codec.ShortCodec;
import com.jun0rr.jbon.codec.Utf8Codec;
import com.jun0rr.jbon.codec.ZonedDateTimeCodec;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author F6036477
 */
public class DefaultBinContext implements BinContext {
  
  private final Map<BinType,BinCodec> codecs;
  
  public DefaultBinContext() {
    this.codecs = new ConcurrentHashMap<>();
    codecs.put(DefaultBinType.BOOLEAN, new BooleanCodec());
    codecs.put(DefaultBinType.BYTE, new ByteCodec());
    codecs.put(DefaultBinType.CHAR, new CharCodec());
    codecs.put(DefaultBinType.DATE, new LocalDateCodec());
    codecs.put(DefaultBinType.DATE_TIME, new LocalDateTimeCodec());
    codecs.put(DefaultBinType.DOUBLE, new DoubleCodec());
    codecs.put(DefaultBinType.FLOAT, new FloatCodec());
    codecs.put(DefaultBinType.INSTANT, new InstantCodec());
    codecs.put(DefaultBinType.INTEGER, new IntegerCodec());
    codecs.put(DefaultBinType.LONG, new LongCodec());
    codecs.put(DefaultBinType.SHORT, new ShortCodec());
    codecs.put(DefaultBinType.UTF8, new Utf8Codec());
    codecs.put(DefaultBinType.ZONED_DATE_TIME, new ZonedDateTimeCodec());
    codecs.put(DefaultBinType.MAP, new MapCodec(this));
    codecs.put(DefaultBinType.LIST, new BooleanCodec());
    codecs.put(DefaultBinType.OBJECT, new BooleanCodec());
  }
  
  public DefaultBinContext(Map<BinType,BinCodec> codecs) {
    this.codecs = Objects.requireNonNull(codecs);
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(BinType<T> type) throws UnexpectedBinTypeException {
    BinCodec c = codecs.get(type);
    if(c == null) throw new UnexpectedBinTypeException(type.id());
    return (BinCodec<T>) c;
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(Class<T> cls) throws BinTypeNotFoundException {
    Optional<BinCodec> opt = codecs.entrySet().stream()
        .filter(e->e.getKey().type() == cls)
        .map(Map.Entry::getValue)
        .findFirst();
    if(opt.isEmpty()) {
      opt = codecs.entrySet().stream()
        .filter(e->e.getKey().type().isAssignableFrom(cls))
        .map(Map.Entry::getValue)
        .findFirst();
    }
    return opt.orElse(cls.isArray() ? codecs.get(DefaultBinType.LIST) : codecs.get(DefaultBinType.OBJECT));
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(long id) throws UnexpectedBinTypeException {
    Optional<BinCodec> opt = codecs.entrySet().stream()
        .filter(e->e.getKey().id() == id)
        .map(Map.Entry::getValue)
        .findFirst();
    return opt.orElseThrow(()->new UnexpectedBinTypeException(id));
  }

  @Override
  public <T> BinType<T> getBinType(Class<T> cls) throws BinTypeNotFoundException {
    Optional<BinType> opt = codecs.keySet().stream()
        .filter(t->t.type() == cls)
        .findFirst();
    if(opt.isEmpty()) {
      opt = codecs.keySet().stream()
        .filter(t->t.type().isAssignableFrom(cls))
        .findFirst();
    }
    return opt.orElse(cls.isArray() ? DefaultBinType.LIST : DefaultBinType.OBJECT);
  }

  @Override
  public <T> BinType<T> getBinType(long id) throws UnexpectedBinTypeException {
    Optional<BinCodec> opt = codecs.entrySet().stream()
        .filter(e->e.getKey().id() == id)
        .map(Map.Entry::getValue)
        .findFirst();
    return (BinType<T>) opt.orElseThrow(()->new UnexpectedBinTypeException(id));
  }

  @Override
  public Map<BinType, BinCodec> codecs() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public <T> T read(ByteBuffer buf) throws UnexpectedBinTypeException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public <T> void write(ByteBuffer buf, T o) throws BinTypeNotFoundException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
  
  @Override
  public int calcSize(Object o) throws BinTypeNotFoundException {
    return -1;
  }
  
}
