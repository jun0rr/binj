/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;
import com.jun0rr.jbom.impl.DefaultIndexedKey;
import com.jun0rr.jbom.impl.Pair;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class MapCodec implements BinCodec<Map> {
  
  private final BinContext ctx;
  
  public MapCodec(BinContext ctx) {
    this.ctx = Objects.requireNonNull(ctx);
  }

  @Override
  public Map read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    List<Pair<IndexedKey,Object>> keys = new ArrayList<>(size);
    for(int i = 0; i < size; i++) {
      IndexedKey k = ctx.read(buf);
      int pos = buf.position();
      buf.position(k.index());
      keys.add(Pair.of(k, ctx.read(buf)));
      buf.position(pos);
    }
    return keys.stream()
        .map(p->p.withKey(p.key().key()))
        .collect(Collectors.toMap(Pair::key, Pair::value));
  }

  @Override
  public void write(BinBuffer buf, Map val) {
    buf.putLong(bintype().id());
    buf.putShort((short)val.size());
    int kpos = buf.position();
    List<Pair<DefaultIndexedKey,Object>> keys = ((Map<Object,Object>)val)
        .entrySet().stream()
        .map(e->Pair.of(new DefaultIndexedKey(e.getKey()), e.getValue()))
        .collect(Collectors.toList());
    AtomicInteger vpos = new AtomicInteger(kpos + keys.stream()
        .map(Pair::key)
        .mapToInt(ctx::calcSize).sum()
    );
    keys = keys.stream().map(p->Pair.of(p.key().with(
        vpos.getAndUpdate(i->i + ctx.calcSize(p.value()))), p.value())
    ).collect(Collectors.toList());
    keys.stream().map(Pair::key).forEach(i->ctx.write(buf, i));
    keys.stream().map(Pair::value).forEach(i->ctx.write(buf, i));
  }

  @Override
  public int calcSize(Map val) {
    int len = ((Map<Object,Object>)val).entrySet().stream()
        .map(e->new AbstractMap.SimpleEntry<>(new DefaultIndexedKey(e.getKey()), e.getValue()))
        .mapToInt(e->ctx.calcSize(e.getKey()) + ctx.calcSize(e.getValue()))
        .sum();
    return Long.BYTES + Short.BYTES + len;
  }

  @Override
  public BinType<Map> bintype() {
    return DefaultBinType.MAP;
  }
  
}
