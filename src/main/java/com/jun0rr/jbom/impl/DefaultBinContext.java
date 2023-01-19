/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.BinTypeNotFoundException;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.codec.BooleanCodec;
import com.jun0rr.jbom.codec.ByteCodec;
import com.jun0rr.jbom.codec.CharCodec;
import com.jun0rr.jbom.codec.CollectionCodec;
import com.jun0rr.jbom.codec.DoubleCodec;
import com.jun0rr.jbom.codec.FloatCodec;
import com.jun0rr.jbom.codec.IndexedKeyCodec;
import com.jun0rr.jbom.codec.InstantCodec;
import com.jun0rr.jbom.codec.IntegerCodec;
import com.jun0rr.jbom.codec.LocalDateCodec;
import com.jun0rr.jbom.codec.LocalDateTimeCodec;
import com.jun0rr.jbom.codec.LongCodec;
import com.jun0rr.jbom.codec.MapCodec;
import com.jun0rr.jbom.codec.ShortCodec;
import com.jun0rr.jbom.codec.Utf8Codec;
import com.jun0rr.jbom.codec.ZonedDateTimeCodec;
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
    codecs.put(DefaultBinType.COLLECTION, new CollectionCodec(this));
    codecs.put(DefaultBinType.DATE, new LocalDateCodec());
    codecs.put(DefaultBinType.DATE_TIME, new LocalDateTimeCodec());
    codecs.put(DefaultBinType.DOUBLE, new DoubleCodec());
    codecs.put(DefaultBinType.FLOAT, new FloatCodec());
    codecs.put(DefaultBinType.IDXKEY, new IndexedKeyCodec(this));
    codecs.put(DefaultBinType.INSTANT, new InstantCodec());
    codecs.put(DefaultBinType.INTEGER, new IntegerCodec());
    codecs.put(DefaultBinType.LONG, new LongCodec());
    codecs.put(DefaultBinType.SHORT, new ShortCodec());
    codecs.put(DefaultBinType.UTF8, new Utf8Codec());
    codecs.put(DefaultBinType.ZONED_DATE_TIME, new ZonedDateTimeCodec());
    codecs.put(DefaultBinType.MAP, new MapCodec(this));
    codecs.put(DefaultBinType.OBJECT, new BooleanCodec());
  }
  
  public DefaultBinContext(Map<BinType,BinCodec> codecs) {
    this.codecs = Objects.requireNonNull(codecs);
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(BinType<T> type) throws BinTypeNotFoundException {
    BinCodec c = codecs.get(type);
    if(c == null) throw new BinTypeNotFoundException(type.type());
    return (BinCodec<T>) c;
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(Class<T> cls) {
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
    return opt.orElse(cls.isArray() ? codecs.get(DefaultBinType.COLLECTION) : codecs.get(DefaultBinType.OBJECT));
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(long id) throws UnknownBinTypeException {
    Optional<BinCodec> opt = codecs.entrySet().stream()
        .filter(e->e.getKey().id() == id)
        .map(Map.Entry::getValue)
        .findFirst();
    return opt.orElseThrow(()->new UnknownBinTypeException(id));
  }

  @Override
  public <T> BinType<T> getBinType(Class<T> cls) {
    Optional<BinType> opt = codecs.keySet().stream()
        .filter(t->t.type() == cls)
        .findFirst();
    if(opt.isEmpty()) {
      opt = codecs.keySet().stream()
        .filter(t->t.type().isAssignableFrom(cls))
        .findFirst();
    }
    return opt.orElse(cls.isArray() ? DefaultBinType.COLLECTION : DefaultBinType.OBJECT);
  }

  @Override
  public <T> BinType<T> getBinType(long id) throws UnknownBinTypeException {
    Optional<BinCodec> opt = codecs.entrySet().stream()
        .filter(e->e.getKey().id() == id)
        .map(Map.Entry::getValue)
        .findFirst();
    return (BinType<T>) opt.orElseThrow(()->new UnknownBinTypeException(id));
  }

  @Override
  public Map<BinType, BinCodec> codecs() {
    return codecs;
  }

  @Override
  public <T> T read(ByteBuffer buf) throws UnknownBinTypeException {
    int pos = buf.position();
    long id = buf.getLong();
    buf.position(pos);
    BinCodec<T> c = getBinCodec(id);
    return c.read(buf);
  }

  @Override
  public <T> void write(ByteBuffer buf, T o) throws BinTypeNotFoundException {
    BinCodec c = getBinCodec(o.getClass());
    c.write(buf, o);
  }
  
  @Override
  public int calcSize(Object o) throws BinTypeNotFoundException {
    BinCodec c = getBinCodec(o.getClass());
    return c.calcSize(o);
  }
  
}
