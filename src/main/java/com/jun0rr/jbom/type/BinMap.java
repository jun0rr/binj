/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.type;

import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.codec.IndexedKey;
import com.jun0rr.jbom.impl.DefaultBinType;
import com.jun0rr.jbom.impl.Pair;
import com.jun0rr.jbom.impl.SupplierIterator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author F6036477
 */
public class BinMap<K,V> implements Map<K,V> {
  
  private final BinContext ctx;
  
  private final ByteBuffer buf;
  
  private final int size;
  
  private final int kpos;
  
  public BinMap(BinContext ctx, ByteBuffer buf) {
    this.ctx = Objects.requireNonNull(ctx);
    this.buf = Objects.requireNonNull(buf).slice();
    long id = buf.getLong();
    if(id != DefaultBinType.MAP.id()) {
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
  public boolean containsKey(Object key) {
    if(key == null) return false;
    buf.position(kpos);
    for(int i = 0; i < size; i++) {
      IndexedKey k = ctx.read(buf);
      if(key.equals(k.key())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsValue(Object value) {
    if(value == null) return false;
    buf.position(kpos);
    IndexedKey k = ctx.read(buf);
    buf.position(k.index());
    for(int i = 0; i < size; i++) {
      Object v = ctx.read(buf);
      if(value.equals(v)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public V get(Object key) {
    if(key == null) return null;
    buf.position(kpos);
    for(int i = 0; i < size; i++) {
      IndexedKey k = ctx.read(buf);
      if(key.equals(k.key())) {
        buf.position(k.index());
        return ctx.read(buf);
      }
    }
    return null;
  }

  @Override
  public V put(K key, V value) {
    throw new UnsupportedOperationException("Read-Only binary Map");
  }

  @Override
  public V remove(Object key) {
    throw new UnsupportedOperationException("Read-Only binary Map");
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    throw new UnsupportedOperationException("Read-Only binary Map");
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Read-Only binary Map");
  }

  @Override
  public Set<K> keySet() {
    Set<K> keys = new TreeSet<>();
    buf.position(kpos);
    for(int i = 0; i < size; i++) {
      IndexedKey k = ctx.read(buf);
      keys.add((K)k.key());
    }
    return keys;
  }

  @Override
  public Collection<V> values() {
    List<V> vs = new ArrayList<>(size);
    buf.position(kpos);
    IndexedKey k = ctx.read(buf);
    buf.position(k.index());
    for(int i = 0; i < size; i++) {
      vs.add(ctx.read(buf));
    }
    return vs;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K,V>> set = new TreeSet<>();
    buf.position(kpos);
    for(int i = 0; i < size; i++) {
      IndexedKey k = ctx.read(buf);
      int pos = buf.position();
      buf.position(k.index());
      set.add(Pair.of((K)k.key(), (V)ctx.read(buf)));
      buf.position(pos);
    }
    return set;
  }
  
  public Stream<K> streamKeys() {
    ByteBuffer bb = buf.position(kpos).slice();
    return new SupplierIterator(size, ()->ctx.<IndexedKey>read(bb).key()).stream();
  }
  
  public Stream<V> streamValues() {
    buf.position(kpos);
    IndexedKey key = ctx.read(buf);
    ByteBuffer bb = buf.position(key.index()).slice();
    return new SupplierIterator(size, ()->ctx.read(bb)).stream();
  }
  
  public Stream<Pair<K,V>> streamEntries() {
    ByteBuffer bb = buf.position(0).slice().position(kpos);
    Supplier<Pair<K,V>> sup = ()->{
      IndexedKey k = ctx.read(bb);
      int pos = bb.position();
      bb.position(k.index());
      Pair<K,V> p = Pair.of((K)k.key(), ctx.read(bb));
      bb.position(pos);
      return p;
    };
    return new SupplierIterator(size, sup).stream();
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 43 * hash + Objects.hashCode(this.buf);
    hash = 43 * hash + this.size;
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
    final BinMap<?, ?> other = (BinMap<?, ?>) obj;
    if (this.size != other.size) {
      return false;
    }
    if (this.kpos != other.kpos) {
      return false;
    }
    return Objects.equals(this.buf, other.buf);
  }

  @Override
  public String toString() {
    return "BinMap{" + "ctx=" + ctx + ", buf=" + buf + ", size=" + size + ", kpos=" + kpos + '}';
  }
  
  public String printContent() {
    StringBuilder sb = new StringBuilder("{");
    streamEntries()
        .forEach(p->sb.append(p.key()).append("=").append(p.value()).append(", "));
    sb.delete(sb.length() -2, sb.length());
    return sb.append("}").toString();
  }
  
}
