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
public class FloatArrayCodec extends AbstractBinCodec<float[]> {
  
  public FloatArrayCodec() {
    super(DefaultBinType.FLOAT_ARRAY);
  }
  
  @Override
  public float[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    float[] array = new float[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getFloat();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, float[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putFloat(array[i]);
    }
  }

  @Override
  public int calcSize(float[] array) {
    return Long.BYTES + Short.BYTES + Float.BYTES * array.length;
  }

}
