/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class DefaultBinType<T> implements BinType<T> {
  
  public static final BinType<Byte> BYTE = new PrimitiveBinType(byte.class, Byte.class);
  
  public static final BinType<byte[]> BYTE_ARRAY = new DefaultBinType(byte[].class);
  
  public static final BinType<Character> CHAR = new PrimitiveBinType(char.class, Character.class);
  
  public static final BinType<char[]> CHAR_ARRAY = new DefaultBinType(char[].class);
  
  public static final BinType<Boolean> BOOLEAN = new PrimitiveBinType(boolean.class, Boolean.class);
  
  public static final BinType<boolean[]> BOOLEAN_ARRAY = new DefaultBinType(boolean[].class);
  
  public static final BinType<Short> SHORT = new PrimitiveBinType(short.class, Short.class);
  
  public static final BinType<short[]> SHORT_ARRAY = new DefaultBinType(short[].class);
  
  public static final BinType<Integer> INTEGER = new PrimitiveBinType(int.class, Integer.class);
  
  public static final BinType<int[]> INT_ARRAY = new DefaultBinType(int[].class);
  
  public static final BinType<Long> LONG = new PrimitiveBinType(long.class, Long.class);
  
  public static final BinType<long[]> LONG_ARRAY = new DefaultBinType(long[].class);
  
  public static final BinType<Float> FLOAT = new PrimitiveBinType(float.class, Float.class);
  
  public static final BinType<float[]> FLOAT_ARRAY = new DefaultBinType(float[].class);
  
  public static final BinType<Double> DOUBLE = new PrimitiveBinType(double.class, Double.class);
  
  public static final BinType<double[]> DOUBLE_ARRAY = new DefaultBinType(double[].class);
  
  public static final BinType<String> UTF8 = new DefaultBinType(String.class);
  
  public static final BinType<Class> CLASS = new DefaultBinType(Class.class);
  
  public static final BinType<Enum> ENUM = new DefaultBinType(Enum.class);
  
  public static final BinType<LocalDate> DATE = new DefaultBinType(LocalDate.class);
  
  public static final BinType<LocalDateTime> DATE_TIME = new DefaultBinType(LocalDateTime.class);
  
  public static final BinType<ZonedDateTime> ZONED_DATE_TIME = new DefaultBinType(ZonedDateTime.class);
  
  public static final BinType<Instant> INSTANT = new DefaultBinType(Instant.class);
  
  public static final BinType<IndexedKey> IDXKEY = new DefaultBinType(IndexedKey.class);
  
  public static final BinType<Map> MAP = new DefaultBinType(Map.class);
  
  public static final BinType<Collection> COLLECTION = new DefaultBinType(Collection.class);
  
  public static final List<BinType> DEFAULT_TYPES = List.of(
      BYTE,             BYTE_ARRAY, CHAR,         CHAR_ARRAY,   BOOLEAN, 
      BOOLEAN_ARRAY,    SHORT,      SHORT_ARRAY,  INTEGER,      INT_ARRAY, 
      LONG,             LONG_ARRAY, FLOAT,        FLOAT_ARRAY,  DOUBLE, 
      DOUBLE_ARRAY,     UTF8,       CLASS,        DATE,         DATE_TIME, 
      ZONED_DATE_TIME,  INSTANT,    IDXKEY,       MAP,          COLLECTION
  );
  
  
  private final long id;
  
  private final Class<T> type;
  
  public DefaultBinType(long id, Class<T> type) {
    this.id = id;
    this.type = Objects.requireNonNull(type);
  }
  
  public DefaultBinType(Class<T> type) {
    this(BinType.genId(type), type);
  }
  
  @Override
  public long id() {
    return id;
  }
  
  @Override
  public Class<T> type() {
    return type;
  }

  @Override
  public boolean isTypeOf(Class cls) {
    return type == cls || type.isAssignableFrom(cls);
  }

  @Override
  public boolean isTypeOf(long id) {
    return this.id == id;
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 17 * hash + (int)this.id;
    hash = 17 * hash + Objects.hashCode(this.type);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DefaultBinType other = (DefaultBinType) obj;
    if (this.id != other.id) {
      return false;
    }
    return Objects.equals(this.type, other.type);
  }

  @Override
  public String toString() {
    return "BinType{" + "type=" + type.getCanonicalName() + ", id=" + id + '}';
  }

}
