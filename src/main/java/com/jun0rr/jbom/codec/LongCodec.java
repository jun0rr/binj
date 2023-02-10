/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;

/**
 *
 * @author F6036477
 */
public class LongCodec extends AbstractBinCodec<Long> {
  
  public LongCodec() {
    super(DefaultBinType.LONG);
  }
  
  @Override
  public Long read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.getLong();
  }
  
  @Override
  public void write(BinBuffer buf, Long val) {
    buf.putLong(bintype().id());
    buf.putLong(val);
  }

  @Override
  public int calcSize(Long val) {
    return Long.BYTES * 2;
  }

}
