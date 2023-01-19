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

/**
 *
 * @author F6036477
 */
public class BooleanCodec implements BinCodec<Boolean> {
  
  @Override
  public BinType<Boolean> bintype() {
    return DefaultBinType.BOOLEAN;
  }

  @Override
  public Boolean read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.get() == 1 ? Boolean.TRUE : Boolean.FALSE;
  }
  
  @Override
  public void write(ByteBuffer buf, Boolean val) {
    buf.putLong(bintype().id());
    buf.put((byte)(val ? 1 : 0));
  }

  @Override
  public int calcSize(Boolean val) {
    return Long.BYTES + Byte.BYTES;
  }

}
