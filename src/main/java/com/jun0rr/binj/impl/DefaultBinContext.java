/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.impl;

import com.jun0rr.binj.BinCodec;
import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.BinType;
import com.jun0rr.binj.BinTypeNotFoundException;
import com.jun0rr.binj.ContextEvent;
import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.codec.ArrayCodec;
import com.jun0rr.binj.codec.BooleanCodec;
import com.jun0rr.binj.codec.ByteCodec;
import com.jun0rr.binj.codec.CharCodec;
import com.jun0rr.binj.codec.ClassCodec;
import com.jun0rr.binj.codec.CollectionCodec;
import com.jun0rr.binj.codec.DoubleCodec;
import com.jun0rr.binj.codec.FloatCodec;
import com.jun0rr.binj.codec.IndexedKeyCodec;
import com.jun0rr.binj.codec.InstantCodec;
import com.jun0rr.binj.codec.IntegerCodec;
import com.jun0rr.binj.codec.LocalDateCodec;
import com.jun0rr.binj.codec.LocalDateTimeCodec;
import com.jun0rr.binj.codec.LongCodec;
import com.jun0rr.binj.codec.MapCodec;
import com.jun0rr.binj.codec.ObjectCodec;
import com.jun0rr.binj.codec.ShortCodec;
import com.jun0rr.binj.codec.Utf8Codec;
import com.jun0rr.binj.codec.ZonedDateTimeCodec;
import com.jun0rr.binj.mapping.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.jun0rr.binj.ContextListener;
import com.jun0rr.binj.codec.BinTypeCodec;
import com.jun0rr.binj.codec.BooleanArrayCodec;
import com.jun0rr.binj.codec.ByteArrayCodec;
import com.jun0rr.binj.codec.CharArrayCodec;
import com.jun0rr.binj.codec.DoubleArrayCodec;
import com.jun0rr.binj.codec.EnumCodec;
import com.jun0rr.binj.codec.FloatArrayCodec;
import com.jun0rr.binj.codec.IntArrayCodec;
import com.jun0rr.binj.codec.LongArrayCodec;
import com.jun0rr.binj.codec.ShortArrayCodec;

/**
 *
 * @author F6036477
 */
public class DefaultBinContext implements BinContext {
  
  private final ObjectMapper mapper;
  
  private final Map<BinType, BinCodec> codecs;
  
  private final List<ContextListener> listeners;
  
  public DefaultBinContext(ObjectMapper mapper) {
    this.mapper = Objects.requireNonNull(mapper);
    this.codecs = new ConcurrentHashMap<>();
    this.listeners = new CopyOnWriteArrayList<>();
    codecs.put(DefaultBinType.BOOLEAN, new BooleanCodec());
    codecs.put(DefaultBinType.BOOLEAN_ARRAY, new BooleanArrayCodec());
    codecs.put(DefaultBinType.BYTE, new ByteCodec());
    codecs.put(DefaultBinType.BYTE_ARRAY, new ByteArrayCodec());
    codecs.put(DefaultBinType.CHAR, new CharCodec());
    codecs.put(DefaultBinType.CHAR_ARRAY, new CharArrayCodec());
    codecs.put(DefaultBinType.SHORT, new ShortCodec());
    codecs.put(DefaultBinType.SHORT_ARRAY, new ShortArrayCodec());
    codecs.put(DefaultBinType.INTEGER, new IntegerCodec());
    codecs.put(DefaultBinType.INT_ARRAY, new IntArrayCodec());
    codecs.put(DefaultBinType.LONG, new LongCodec());
    codecs.put(DefaultBinType.LONG_ARRAY, new LongArrayCodec());
    codecs.put(DefaultBinType.FLOAT, new FloatCodec());
    codecs.put(DefaultBinType.FLOAT_ARRAY, new FloatArrayCodec());
    codecs.put(DefaultBinType.DOUBLE, new DoubleCodec());
    codecs.put(DefaultBinType.DOUBLE_ARRAY, new DoubleArrayCodec());
    codecs.put(DefaultBinType.UTF8, new Utf8Codec());
    codecs.put(DefaultBinType.BINTYPE, new BinTypeCodec());
    codecs.put(DefaultBinType.CLASS, new ClassCodec());
    codecs.put(DefaultBinType.DATE, new LocalDateCodec());
    codecs.put(DefaultBinType.DATE_TIME, new LocalDateTimeCodec());
    codecs.put(DefaultBinType.INSTANT, new InstantCodec());
    codecs.put(DefaultBinType.ZONED_DATE_TIME, new ZonedDateTimeCodec());
    codecs.put(DefaultBinType.IDXKEY, new IndexedKeyCodec(this));
    codecs.put(DefaultBinType.COLLECTION, new CollectionCodec(this));
    codecs.put(DefaultBinType.MAP, new MapCodec(this));
    codecs.put(DefaultBinType.ENUM, new EnumCodec(this));
  }
  
  public DefaultBinContext(ObjectMapper mapper, Map<BinType, BinCodec> codecs) {
    this.mapper = Objects.requireNonNull(mapper);
    this.codecs = Objects.requireNonNull(codecs);
    this.listeners = new CopyOnWriteArrayList<>();
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(BinType<T> type) throws BinTypeNotFoundException {
    return codecs.entrySet().stream()
        .filter(e->e.getKey().equals(type))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseThrow(()->new BinTypeNotFoundException(type.type()));
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(Class<T> cls) {
    Optional<BinCodec> opt = codecs.entrySet().stream()
        .filter(e->e.getKey().isTypeOf(cls))
        .map(Map.Entry::getValue)
        .findFirst();
    if(opt.isEmpty()) {
      BinType<T> type = new DefaultBinType(cls);
      BinCodec<T> codec;
      if(cls.isEnum()) {
        codec = (BinCodec<T>) new EnumCodec(this);
      }
      else if(cls.isArray()) {
        codec = new ArrayCodec(this, type);
      }
      else {
        codec = new ObjectCodec(this,type);
      }
      codecs.put(type, codec);
      opt = Optional.of(codec);
    }
    return opt.get();
  }
  
  @Override
  public <T> BinCodec<T> getBinCodec(long id) throws UnknownBinTypeException {
    return codecs.entrySet().stream()
        .filter(e->e.getKey().isTypeOf(id))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseThrow(()->new UnknownBinTypeException(id));
  }

  @Override
  public <T> BinType<T> getBinType(Class<T> cls) throws BinTypeNotFoundException {
    return codecs.entrySet().stream()
        .filter(e->e.getKey().isTypeOf(cls))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow(()->new BinTypeNotFoundException(cls));
  }

  @Override
  public <T> BinType<T> getBinType(long id) throws UnknownBinTypeException {
    return codecs.entrySet().stream()
        .filter(e->e.getKey().isTypeOf(id))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow(()->new UnknownBinTypeException(id));
  }
  
  @Override
  public BinType putIfAbsent(BinType t, BinCodec c) {
    Optional<BinType> opt = codecs.keySet().stream()
        .filter(k->k.isTypeOf(t.type()))
        .findAny();
    if(opt.isEmpty()) {
      codecs.put(t, c);
    }
    return opt.orElse(t);
  }

  @Override
  public BinType putIfAbsent(Class c, BinCodec d) {
    Optional<BinType> opt = codecs.keySet().stream()
        .filter(k->k.isTypeOf(c))
        .findAny();
    if(opt.isEmpty()) {
      BinType t = new DefaultBinType(c);
      codecs.put(t, d);
      return t;
    }
    return opt.get();
  }

  @Override
  public Map<BinType, BinCodec> codecs() {
    return codecs;
  }
  
  @Override
  public ObjectMapper mapper() {
    return mapper;
  }

  @Override
  public <T> T read(BinBuffer buf) throws UnknownBinTypeException {
    String sbuf = buf.toString();
    int pos = buf.position();
    BinCodec<T> c = getBinCodec(buf.getLong());
    buf.position(pos);
    T o = c.read(buf);
    //System.out.printf("BinContext.read( %s ): obj=%s%n", buf, o);
    if(!listeners.isEmpty()) {
      int lim = buf.limit();
      int pos2 = buf.position();
      buf.position(pos).limit(pos2);
      ContextEvent evt = new DefaultContextEvent(c, buf.remaining(), buf.checksum());
      buf.limit(lim).position(pos2);
      listeners.forEach(x->x.read(evt));
    }
    return o;
  }

  @Override
  public <T> ContextEvent write(BinBuffer buf, T o) throws BinTypeNotFoundException {
    //System.out.printf("BinContext.write( %s, %s )%n", buf, o);
    String sbuf = buf.toString();
    BinCodec c = getBinCodec(o.getClass());
    int pos = buf.position();
    //System.out.printf("[1]BinContext.write( %s, %s ): codec=%s%n", buf, o.getClass().getCanonicalName(), c.bintype());
    c.write(buf, o);
    //System.out.printf("[2]BinContext.write( %s, %s ): buf=%s%n", sbuf, o.getClass().getCanonicalName(), buf);
    int lim = buf.limit();
    int pos2 = buf.position();
    buf.position(pos).limit(pos2);
    ContextEvent evt = new DefaultContextEvent(c, buf.remaining(), buf.checksum());
    buf.limit(lim).position(pos2);
    listeners.forEach(x->x.write(evt));
    return evt;
  }
  
  @Override
  public int calcSize(Object o) throws BinTypeNotFoundException {
    BinCodec c = getBinCodec(o.getClass());
    return c.calcSize(o);
  }
  
  @Override
  public List<ContextListener> listeners() {
    return listeners;
  }
  
}
