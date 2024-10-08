/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class CollectionCodec extends AbstractBinCodec<Collection> {
  
  private final BinContext ctx;
  
  public CollectionCodec(BinContext ctx) {
    super(DefaultBinType.COLLECTION);
    this.ctx = Objects.requireNonNull(ctx);
  }
  
  @Override
  public Collection read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getInt();
    buf.position(buf.position() + Integer.BYTES * size);
    List ls = new ArrayList(size);
    for(int i = 0; i < size; i++) {
      ls.add(ctx.read(buf));
    }
    return ls;
  }
  
  @Override
  public void write(BinBuffer buf, Collection val) {
    //System.out.printf("CollectionCodec.write[1]( %s, %s ): %n", buf, val);
    buf.putLong(bintype().id());
    //System.out.printf("CollectionCodec.write[2]( %s, %s ): %n", buf, val);
    buf.putInt(val.size());
    //System.out.printf("CollectionCodec.write[3]( %s, %s ): %n", buf, val);
    int kpos = buf.position();
    buf.position(kpos + Integer.BYTES * val.size());
    //System.out.printf("CollectionCodec.write[4]( %s, %s ): %n", buf, val);
    List<Integer> idx = new ArrayList<>(val.size());
    Iterator it = val.iterator();
    int lpos = buf.position();
    while(it.hasNext()) {
      idx.add(buf.position());
      ctx.write(buf, it.next());
      lpos = buf.position();
    }
    buf.position(kpos);
    idx.forEach(i->buf.putInt(i.shortValue()));
    buf.position(lpos);
  }

  @Override
  public int calcSize(Collection val) {
    int size = val.stream().mapToInt(o->ctx.calcSize(o)).sum();
    return Long.BYTES + Integer.BYTES + Integer.BYTES * val.size() + size;
  }

}
