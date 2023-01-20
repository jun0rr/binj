/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.type;

import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.impl.DefaultBinType;
import com.jun0rr.jbom.impl.SupplierIterator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class BinList<T> implements List<T> {

  private final BinContext ctx;
  
  private final ByteBuffer buf;
  
  private final int size;
  
  private final int kpos;
  
  public BinList(BinContext ctx, ByteBuffer bb) {
    this.ctx = Objects.requireNonNull(ctx);
    this.buf = Objects.requireNonNull(bb).slice();
    long id = buf.getLong();
    if(id != DefaultBinType.COLLECTION.id()) {
      throw new UnknownBinTypeException(id);
    }
    this.size = buf.getShort();
    this.kpos = buf.position();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size < 1;
  }
  
  @Override
  public boolean contains(Object o) {
    buf.position(kpos + Short.BYTES * size);
    for(int i = 0; i < size; i++) {
      Object r = ctx.read(buf);
      if(o.equals(r)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    ByteBuffer bb = buf.position(0).slice()
        .position(kpos + Short.BYTES * size);
    return new SupplierIterator(size, ()->ctx.read(bb));
  }

  @Override
  public Object[] toArray() {
    buf.position(kpos + Short.BYTES * size);
    Object[] os = new Object[size];
    for(int i = 0; i < size; i++) {
      os[i] = ctx.read(buf);
    }
    return os;
  }

  @Override
  public <T> T[] toArray(T[] os) {
    buf.position(kpos + Short.BYTES * size);
    for(int i = 0; i < size; i++) {
      os[i] = ctx.read(buf);
    }
    return os;
  }

  @Override
  public boolean add(T e) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    buf.position(kpos + Short.BYTES * size);
    int contains = 0;
    for(int i = 0; i < size; i++) {
      Object r = ctx.read(buf);
      if(c.stream().anyMatch(o->o.equals(r))) {
        contains++;
      }
    }
    return contains == c.size();
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public T get(int index) {
    if(index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(index);
    }
    buf.position(kpos + Short.BYTES * index);
    buf.position(buf.getShort());
    return ctx.read(buf);
  }

  @Override
  public T set(int index, T element) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public void add(int index, T element) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public T remove(int index) {
    throw new UnsupportedOperationException("Read-Only binary List");
  }

  @Override
  public int indexOf(Object o) {
    buf.position(kpos + Short.BYTES * size);
    for(int i = 0; i < size; i++) {
      if(o.equals(ctx.read(buf))) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    int[] idx = new int[size];
    buf.position(kpos);
    for(int i = 0; i < size; i++) {
      idx[0] = buf.getShort();
    }
    for(int i = idx.length -1; i >= 0; i--) {
      buf.position(idx[i]);
      if(o.equals(ctx.read(buf))) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public ListIterator<T> listIterator() {
    List<T> ls = new ArrayList<>(size);
    buf.position(kpos + Short.BYTES * size);
    for(int i = 0; i < size; i++) {
      ls.add(ctx.read(buf));
    }
    return ls.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    if(index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(index);
    }
    List<T> ls = new ArrayList<>(size - index);
    buf.position(kpos + Short.BYTES * index);
    for(int i = index; i < size; i++) {
      int vpos = buf.getShort();
      int pos = buf.position();
      buf.position(vpos);
      ls.add(ctx.read(buf));
      buf.position(pos);
    }
    return ls.listIterator();
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    if(fromIndex < 0 || fromIndex >= size || fromIndex >= toIndex) {
      throw new IndexOutOfBoundsException(fromIndex);
    }if(toIndex < 0 || toIndex > size || toIndex <= fromIndex) {
      throw new IndexOutOfBoundsException(toIndex);
    }
    List<T> ls = new ArrayList<>(size);
    buf.position(kpos + Short.BYTES * fromIndex);
    for(int i = fromIndex; i < toIndex; i++) {
      int vpos = buf.getShort();
      int pos = buf.position();
      buf.position(vpos);
      ls.add(ctx.read(buf));
      buf.position(pos);
    }
    return ls;
  }
  
}
