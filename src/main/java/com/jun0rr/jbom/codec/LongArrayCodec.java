/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;

/**
 *
 * @author F6036477
 */
public class LongArrayCodec implements BinCodec<long[]> {
  
  @Override
  public BinType<long[]> bintype() {
    return DefaultBinType.LONG_ARRAY;
  }

  @Override
  public long[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    long[] array = new long[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getLong();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, long[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putLong(array[i]);
    }
  }

  @Override
  public int calcSize(long[] array) {
    return Long.BYTES + Long.BYTES + Long.BYTES * array.length;
  }

}
