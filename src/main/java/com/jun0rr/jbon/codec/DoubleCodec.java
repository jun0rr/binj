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
public class DoubleCodec implements BinCodec<Double> {
  
  @Override
  public BinType<Double> bintype() {
    return DefaultBinType.DOUBLE;
  }

  @Override
  public Double read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    return buf.getDouble();
  }
  
  @Override
  public void write(ByteBuffer buf, Double val) {
    buf.putLong(bintype().id());
    buf.putDouble(val);
  }

  @Override
  public int calcSize(Double val) {
    return Long.BYTES + Double.BYTES;
  }

}
