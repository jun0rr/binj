/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.BinTypeNotFoundException;
import com.jun0rr.jbom.ContextObserver;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.WriteEvent;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.codec.ArrayCodec;
import com.jun0rr.jbom.codec.BooleanCodec;
import com.jun0rr.jbom.codec.ByteCodec;
import com.jun0rr.jbom.codec.CharCodec;
import com.jun0rr.jbom.codec.ClassCodec;
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
import com.jun0rr.jbom.codec.ObjectCodec;
import com.jun0rr.jbom.codec.ShortCodec;
import com.jun0rr.jbom.codec.Utf8Codec;
import com.jun0rr.jbom.codec.ZonedDateTimeCodec;
import com.jun0rr.jbom.mapping.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author F6036477
 */
public class DefaultBinContext implements BinContext {
  
  private final ObjectMapper mapper;
  
  private final Map<BinType, BinCodec> codecs;
  
  private final List<ContextObserver> observers;
  
  public DefaultBinContext(ObjectMapper mapper) {
    this.mapper = Objects.requireNonNull(mapper);
    this.codecs = new ConcurrentHashMap<>();
    this.observers = new CopyOnWriteArrayList<>();
    codecs.put(DefaultBinType.BOOLEAN, new BooleanCodec());
    codecs.put(DefaultBinType.BYTE, new ByteCodec());
    codecs.put(DefaultBinType.CHAR, new CharCodec());
    codecs.put(DefaultBinType.SHORT, new ShortCodec());
    codecs.put(DefaultBinType.INTEGER, new IntegerCodec());
    codecs.put(DefaultBinType.LONG, new LongCodec());
    codecs.put(DefaultBinType.FLOAT, new FloatCodec());
    codecs.put(DefaultBinType.DOUBLE, new DoubleCodec());
    codecs.put(DefaultBinType.UTF8, new Utf8Codec());
    codecs.put(DefaultBinType.CLASS, new ClassCodec());
    codecs.put(DefaultBinType.DATE, new LocalDateCodec());
    codecs.put(DefaultBinType.DATE_TIME, new LocalDateTimeCodec());
    codecs.put(DefaultBinType.INSTANT, new InstantCodec());
    codecs.put(DefaultBinType.ZONED_DATE_TIME, new ZonedDateTimeCodec());
    codecs.put(DefaultBinType.IDXKEY, new IndexedKeyCodec(this));
    codecs.put(DefaultBinType.COLLECTION, new CollectionCodec(this));
    codecs.put(DefaultBinType.MAP, new MapCodec(this));
  }
  
  public DefaultBinContext(ObjectMapper mapper, Map<BinType, BinCodec> codecs) {
    this.mapper = Objects.requireNonNull(mapper);
    this.codecs = Objects.requireNonNull(codecs);
    this.observers = new CopyOnWriteArrayList<>();
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
      BinCodec<T> codec = cls.isArray() ? new ArrayCodec(this, type) : new ObjectCodec(this, mapper, type);
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
    int pos = buf.position();
    long id = buf.getLong();
    buf.position(pos);
    BinCodec<T> c = getBinCodec(id);
    //System.out.printf("DefaultBinContext.read( %s ): codec=%s%n", buf, c);
    observers.forEach(x->x.read(c.bintype()));
    return c.read(buf);
  }

  @Override
  public <T> void write(BinBuffer buf, T o) throws BinTypeNotFoundException {
    BinCodec c = getBinCodec(o.getClass());
    //System.out.printf("DefaultBinContext.write( %s, %s ): codec=%s%n", buf, o, c);
    int pos = buf.position();
    c.write(buf, o);
    if(!observers.isEmpty()) {
      int lim = buf.limit();
      int pos2 = buf.position();
      buf.position(pos).limit(pos2);
      WriteEvent e = new DefaultWriteEvent(c.bintype(), buf.hash("SHA-1"), pos2 - pos);
      buf.limit(lim).position(pos2);
      observers.forEach(x->x.write(e));
    }
  }
  
  @Override
  public int calcSize(Object o) throws BinTypeNotFoundException {
    BinCodec c = getBinCodec(o.getClass());
    return c.calcSize(o);
  }
  
  @Override
  public List<ContextObserver> observers() {
    return observers;
  }
  
}
