/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.impl.DefaultBinType;
import com.jun0rr.jbom.impl.DefaultIndexedKey;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class IndexedKeyCodec implements BinCodec<IndexedKey> {
  
  private final BinContext ctx;
  
  public IndexedKeyCodec(BinContext ctx) {
    this.ctx = Objects.requireNonNull(ctx);
  }

  @Override
  public IndexedKey<IndexedKey> read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int idx = buf.getShort();
    return new DefaultIndexedKey(idx, ctx.read(buf));
  }

  @Override
  public void write(ByteBuffer buf, IndexedKey val) {
    buf.putLong(bintype().id());
    buf.putShort((short)val.index());
    ctx.write(buf, val.key());
  }

  @Override
  public int calcSize(IndexedKey val) {
    return Long.BYTES + Short.BYTES + ctx.calcSize(val.key());
  }

  @Override
  public BinType bintype() {
    return DefaultBinType.IDXKEY;
  }
  
}
