/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinType;
import java.util.Objects;
import com.jun0rr.jbom.ContextListener.ContextEvent;

/**
 *
 * @author F6036477
 */
public class DefaultContextEvent implements ContextEvent {
  
  private final BinType type;
  
  private final long checksum;
  
  private final int size;
  
  public DefaultContextEvent(BinType t, int size, long checksum) {
    this.type = Objects.requireNonNull(t);
    this.checksum = Objects.requireNonNull(checksum);
    this.size = size;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public long checksum() {
    return checksum;
  }
  
  @Override
  public BinType type() {
    return type;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.type);
    hash = 37 * hash + (int) (this.checksum ^ (this.checksum >>> 32));
    hash = 37 * hash + this.size;
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
    final DefaultContextEvent other = (DefaultContextEvent) obj;
    if (this.checksum != other.checksum) {
      return false;
    }
    if (this.size != other.size) {
      return false;
    }
    return Objects.equals(this.type, other.type);
  }

  @Override
  public String toString() {
    return "DefaultWriteEvent{" + "type=" + type + ", checksum=" + checksum + ", size=" + size + '}';
  }

}
