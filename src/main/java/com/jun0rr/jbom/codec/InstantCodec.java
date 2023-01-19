/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.impl.DefaultBinType;
import java.nio.ByteBuffer;
import java.time.Instant;

/**
 *
 * @author F6036477
 */
public class InstantCodec implements BinCodec<Instant> {
  
  @Override
  public BinType<Instant> bintype() {
    return DefaultBinType.INSTANT;
  }

  @Override
  public Instant read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return Instant.ofEpochMilli(buf.getLong());
  }
  
  @Override
  public void write(ByteBuffer buf, Instant val) {
    buf.putLong(bintype().id());
    buf.putLong(val.toEpochMilli());
  }

  @Override
  public int calcSize(Instant val) {
    return Long.BYTES * 2;
  }

}
