/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.codec.IndexedKey;
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
  
  public static final BinType<Byte> BYTE = new DefaultBinType(Byte.class);
  
  public static final BinType<Character> CHAR = new DefaultBinType(Character.class);
  
  public static final BinType<Boolean> BOOLEAN = new DefaultBinType(Boolean.class);
  
  public static final BinType<Short> SHORT = new DefaultBinType(Short.class);
  
  public static final BinType<Integer> INTEGER = new DefaultBinType(Integer.class);
  
  public static final BinType<Long> LONG = new DefaultBinType(Long.class);
  
  public static final BinType<Float> FLOAT = new DefaultBinType(Float.class);
  
  public static final BinType<Double> DOUBLE = new DefaultBinType(Double.class);
  
  public static final BinType<String> UTF8 = new DefaultBinType(String.class);
  
  public static final BinType<LocalDate> DATE = new DefaultBinType(LocalDate.class);
  
  public static final BinType<LocalDateTime> DATE_TIME = new DefaultBinType(LocalDateTime.class);
  
  public static final BinType<ZonedDateTime> ZONED_DATE_TIME = new DefaultBinType(ZonedDateTime.class);
  
  public static final BinType<Instant> INSTANT = new DefaultBinType(Instant.class);
  
  public static final BinType<Object> OBJECT = new DefaultBinType(Object.class);
  
  public static final BinType<IndexedKey> IDXKEY = new DefaultBinType(IndexedKey.class);
  
  public static final BinType<Map> MAP = new DefaultBinType(Map.class);
  
  public static final BinType<Collection> COLLECTION = new DefaultBinType(Collection.class);
  
  
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
    final DefaultBinType<?> other = (DefaultBinType<?>) obj;
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
