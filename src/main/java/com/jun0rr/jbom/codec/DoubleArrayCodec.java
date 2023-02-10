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
public class DoubleArrayCodec extends AbstractBinCodec<double[]> {
  
  public DoubleArrayCodec() {
    super(DefaultBinType.DOUBLE_ARRAY);
  }
  
  @Override
  public double[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    double[] array = new double[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getDouble();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, double[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putDouble(array[i]);
    }
  }

  @Override
  public int calcSize(double[] array) {
    return Long.BYTES + Short.BYTES + Double.BYTES * array.length;
  }

}
