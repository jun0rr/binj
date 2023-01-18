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
public class FloatCodec implements BinCodec<Float> {
  
  @Override
  public BinType<Float> bintype() {
    return DefaultBinType.FLOAT;
  }

  @Override
  public Float read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    return buf.getFloat();
  }
  
  @Override
  public void write(ByteBuffer buf, Float val) {
    buf.putLong(bintype().id());
    buf.putFloat(val);
  }

  @Override
  public int calcSize(Float val) {
    return Long.BYTES + Float.BYTES;
  }

}
