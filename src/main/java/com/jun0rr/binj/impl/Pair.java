/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.impl;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class Pair<K,V> implements Map.Entry<K,V> {
  
  private final K key;
  
  private final V val;
  
  public Pair(K k, V v) {
    this.key = k;
    this.val = v;
  }
  
  public static <X,Y> Pair<X,Y> of(X x, Y y) {
    return new Pair<>(x, y);
  }
  
  public K key() {
    return key;
  }
  
  public V value() {
    return val;
  }
  
  public <X> Pair<X,V> withKey(X x) {
    return Pair.of(x, val);
  }
  
  public <Y> Pair<K,Y> withValue(Y v) {
    return Pair.of(key, v);
  }
  
  @Override
  public K getKey() {
    return key();
  }

  @Override
  public V getValue() {
    return value();
  }

  @Override
  public V setValue(V value) {
    throw new UnsupportedOperationException("Immutable Map.Entry");
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 19 * hash + Objects.hashCode(this.key);
    hash = 19 * hash + Objects.hashCode(this.val);
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
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    if (!Objects.equals(this.key, other.key)) {
      return false;
    }
    return Objects.equals(this.val, other.val);
  }

  @Override
  public String toString() {
    return "Pair{" + "key=" + key + ", val=" + val + '}';
  }

}
