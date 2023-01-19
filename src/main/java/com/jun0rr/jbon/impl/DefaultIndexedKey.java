/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.impl;

import com.jun0rr.jbon.IndexedKey;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class DefaultIndexedKey<T> implements IndexedKey<T> {

  private final int index;
  
  private final T key;
  
  public DefaultIndexedKey(int index, T key) {
    this.index = index;
    this.key = Objects.requireNonNull(key);
  }
  
  public DefaultIndexedKey(T key) {
    this(-1, key);
  }
  
  @Override
  public int index() {
    return index;
  }

  @Override
  public T key() {
    return key;
  }
  
  @Override
  public DefaultIndexedKey<T> with(int index) {
    return new DefaultIndexedKey(index, key);
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + this.index;
    hash = 71 * hash + Objects.hashCode(this.key);
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
    final DefaultIndexedKey<?> other = (DefaultIndexedKey<?>) obj;
    if (this.index != other.index) {
      return false;
    }
    return Objects.equals(this.key, other.key);
  }

  @Override
  public String toString() {
    return "DefaultIndexedKey{" + "index=" + index + ", key=" + key + '}';
  }
  
}
