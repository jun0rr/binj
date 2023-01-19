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
public class IntegerCodec implements BinCodec<Integer> {
  
  @Override
  public BinType<Integer> bintype() {
    return DefaultBinType.INTEGER;
  }

  @Override
  public Integer read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.getInt();
  }
  
  @Override
  public void write(ByteBuffer buf, Integer val) {
    buf.putLong(bintype().id());
    buf.putInt(val);
  }

  @Override
  public int calcSize(Integer val) {
    return Long.BYTES + Integer.BYTES;
  }

}
