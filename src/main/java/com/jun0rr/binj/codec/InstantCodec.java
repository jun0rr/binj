/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;
import java.time.Instant;

/**
 *
 * @author F6036477
 */
public class InstantCodec extends AbstractBinCodec<Instant> {
  
  public InstantCodec() {
    super(DefaultBinType.INSTANT);
  }
  
  @Override
  public Instant read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return Instant.ofEpochMilli(buf.getLong());
  }
  
  @Override
  public void write(BinBuffer buf, Instant val) {
    buf.putLong(bintype().id());
    buf.putLong(val.toEpochMilli());
  }

  @Override
  public int calcSize(Instant val) {
    return Long.BYTES * 2;
  }

}
