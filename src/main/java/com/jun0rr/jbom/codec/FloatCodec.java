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
public class FloatCodec extends AbstractBinCodec<Float> {
  
  public FloatCodec() {
    super(DefaultBinType.FLOAT);
  }
  
  @Override
  public Float read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.getFloat();
  }
  
  @Override
  public void write(BinBuffer buf, Float val) {
    buf.putLong(bintype().id());
    buf.putFloat(val);
  }

  @Override
  public int calcSize(Float val) {
    return Long.BYTES + Float.BYTES;
  }

}
