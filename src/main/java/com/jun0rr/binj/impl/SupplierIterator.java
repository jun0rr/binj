/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.impl;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author F6036477
 */
public class SupplierIterator<T> implements Iterator<T> {
    
  private final int size;

  private int position;

  private final Supplier<T> sup;

  public SupplierIterator(int size, Supplier<T> sup) {
    this.size = size;
    this.sup = Objects.requireNonNull(sup);
    this.position = 0;
  }

  @Override
  public boolean hasNext() {
    return position < size;
  }

  @Override
  public T next() {
    if(!hasNext()) return null;
    position++;
    return sup.get();
  }

  public Stream<T> stream() {
    return StreamSupport.stream(Spliterators.spliterator(this, size, Spliterator.SIZED), false);
  }

}