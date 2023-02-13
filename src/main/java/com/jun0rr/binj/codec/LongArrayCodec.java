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
public class LongArrayCodec extends AbstractBinCodec<long[]> {
  
  public LongArrayCodec() {
    super(DefaultBinType.LONG_ARRAY);
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
    return Long.BYTES + Short.BYTES + Long.BYTES * array.length;
  }

}
