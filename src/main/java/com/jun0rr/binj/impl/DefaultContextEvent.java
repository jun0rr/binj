/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.impl;

import com.jun0rr.binj.BinCodec;
import com.jun0rr.binj.ContextEvent;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class DefaultContextEvent implements ContextEvent {
  
  private final BinCodec codec;
  
  private final long checksum;
  
  private final int size;
  
  public DefaultContextEvent(BinCodec c, int size, long checksum) {
    this.codec = Objects.requireNonNull(c);
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
  public BinCodec codec() {
    return codec;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.codec);
    hash = 97 * hash + (int) (this.checksum ^ (this.checksum >>> 32));
    hash = 97 * hash + this.size;
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
    return Objects.equals(this.codec, other.codec);
  }

  @Override
  public String toString() {
    return "DefaultContextEvent{" + "codec=" + codec + ", size=" + size + ", checksum=" + checksum + '}';
  }
  
}
