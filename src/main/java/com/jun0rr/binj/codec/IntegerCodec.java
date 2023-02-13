/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;

/**
 *
 * @author F6036477
 */
public class IntegerCodec extends AbstractBinCodec<Integer> {
  
  public IntegerCodec() {
    super(DefaultBinType.INTEGER);
  }
  
  @Override
  public Integer read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.getInt();
  }
  
  @Override
  public void write(BinBuffer buf, Integer val) {
    buf.putLong(bintype().id());
    buf.putInt(val);
  }

  @Override
  public int calcSize(Integer val) {
    return Long.BYTES + Integer.BYTES;
  }

}
