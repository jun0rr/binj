/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.BinType;
import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author F6036477
 */
public class EnumCodec extends AbstractBinCodec<Enum> {
  
  private final BinContext ctx;
  
  public EnumCodec(BinContext ctx) {
    super(DefaultBinType.ENUM);
    this.ctx = Objects.requireNonNull(ctx);
  }
  
  @Override
  public Enum read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    BinType t = ctx.getBinType(buf.getLong());
    int ordinal = buf.getShort();
    try {
      Lookup lo = MethodHandles.publicLookup();
      MethodHandle values = lo.unreflect(t.type().getMethod("values"));
      Enum[] es = (Enum[]) values.invoke();
      return es[ordinal];
    }
    catch(Throwable e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public void write(BinBuffer buf, Enum val) {
    Class ec = val.getDeclaringClass();
    buf.putLong(bintype().id());
    BinType bt;
    Optional<BinType> opt = ctx.codecs().keySet().stream()
        .filter(t->t.type() == ec)
        .findAny();
    if(opt.isEmpty()) {
      bt = new DefaultBinType(ec);
      ctx.codecs().put(bt, this);
    }
    else {
      bt = opt.get();
    }
    //System.out.printf("EnumCodec.write( %s, %s ): t=%s, val.class=%s%n", buf, val, bt, val.getDeclaringClass());
    buf.putLong(bt.id());
    buf.putShort((short)val.ordinal());
  }

  @Override
  public int calcSize(Enum val) {
    return Long.BYTES * 2 + Short.BYTES;
  }

}
