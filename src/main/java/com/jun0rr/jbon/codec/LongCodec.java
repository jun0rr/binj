/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.codec;

import com.jun0rr.jbon.BinCodec;
import com.jun0rr.jbon.BinType;
import com.jun0rr.jbon.UnexpectedBinTypeException;
import com.jun0rr.jbon.impl.DefaultBinType;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public class LongCodec implements BinCodec<Long> {
  
  @Override
  public BinType<Long> bintype() {
    return DefaultBinType.LONG;
  }

  @Override
  public Long read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    return buf.getLong();
  }
  
  @Override
  public void write(ByteBuffer buf, Long val) {
    buf.putLong(bintype().id());
    buf.putLong(val);
  }

  @Override
  public int calcSize(Long val) {
    return Long.BYTES * 2;
  }

}
