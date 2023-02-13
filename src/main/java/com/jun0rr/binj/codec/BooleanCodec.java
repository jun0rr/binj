/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.BinType;
import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;

/**
 *
 * @author F6036477
 */
public class BooleanCodec extends AbstractBinCodec<Boolean> {
  
  public BooleanCodec() {
    super(DefaultBinType.BOOLEAN);
  }
  
  @Override
  public Boolean read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.get() == 1 ? Boolean.TRUE : Boolean.FALSE;
  }
  
  @Override
  public void write(BinBuffer buf, Boolean val) {
    buf.putLong(bintype().id());
    buf.put((byte)(val ? 1 : 0));
  }

  @Override
  public int calcSize(Boolean val) {
    return Long.BYTES + Byte.BYTES;
  }

}
