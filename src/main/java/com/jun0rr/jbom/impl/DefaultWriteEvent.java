/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.impl;

import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.WriteEvent;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class DefaultWriteEvent implements WriteEvent {
  
  private final BinType type;
  
  private final byte[] hash;
  
  private final int size;
  
  public DefaultWriteEvent(BinType t, byte[] hash, int size) {
    this.type = Objects.requireNonNull(t);
    this.hash = Objects.requireNonNull(hash);
    this.size = size;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public byte[] hash() {
    return hash;
  }
  
  @Override
  public String hashString() {
    return bytesToHex(hash);
  }

  @Override
  public BinType type() {
    return type;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 13 * hash + Objects.hashCode(this.type);
    hash = 13 * hash + Arrays.hashCode(this.hash);
    hash = 13 * hash + this.size;
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
    final DefaultWriteEvent other = (DefaultWriteEvent) obj;
    if (this.size != other.size) {
      return false;
    }
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    return Arrays.equals(this.hash, other.hash);
  }

  @Override
  public String toString() {
    return "WriteEvent{" + "type=" + type + ", hash=" + hashString() + ", size=" + size + '}';
  }
  
  
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }
  
  private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
  
}
