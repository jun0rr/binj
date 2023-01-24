/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinType;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class PrimitiveBinType<T> implements BinType<T> {
  
  private final Class primitive;
  
  private final Class<T> type;
  
  private final long id;
  
  public PrimitiveBinType(long id, Class primitive, Class<T> type) {
    this.primitive = Objects.requireNonNull(primitive);
    this.type = Objects.requireNonNull(type);
    this.id = id;
  }
  
  public PrimitiveBinType(Class primitive, Class<T> type) {
    this.primitive = Objects.requireNonNull(primitive);
    this.type = Objects.requireNonNull(type);
    this.id = BinType.genId(type);
  }
  
  @Override
  public boolean isTypeOf(Class cls) {
    return primitive == cls || type == cls || type.isAssignableFrom(cls);
  }

  @Override
  public boolean isTypeOf(long id) {
    return this.id == id;
  }

  @Override
  public Class<T> type() {
    return type;
  }

  @Override
  public long id() {
    return id;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.primitive);
    hash = 23 * hash + Objects.hashCode(this.type);
    hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
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
    final PrimitiveBinType other = (PrimitiveBinType) obj;
    if (this.id != other.id) {
      return false;
    }
    if (!Objects.equals(this.primitive, other.primitive)) {
      return false;
    }
    return Objects.equals(this.type, other.type);
  }

  @Override
  public String toString() {
    return "PrimitiveBinType{" + "primitive=" + primitive + ", type=" + type + ", id=" + id + '}';
  }
  
}
