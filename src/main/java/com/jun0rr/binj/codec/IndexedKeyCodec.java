/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;
import com.jun0rr.binj.impl.IndexedKey;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class IndexedKeyCodec extends AbstractBinCodec<IndexedKey> {
  
  private final BinContext ctx;
  
  public IndexedKeyCodec(BinContext ctx) {
    super(DefaultBinType.IDXKEY);
    this.ctx = Objects.requireNonNull(ctx);
  }

  @Override
  public IndexedKey<IndexedKey> read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int idx = buf.getShort();
    return new IndexedKey(idx, ctx.read(buf));
  }

  @Override
  public void write(BinBuffer buf, IndexedKey val) {
    buf.putLong(bintype().id());
    buf.putShort((short)val.index());
    ctx.write(buf, val.key());
  }

  @Override
  public int calcSize(IndexedKey val) {
    return Long.BYTES + Short.BYTES + ctx.calcSize(val.key());
  }

}
