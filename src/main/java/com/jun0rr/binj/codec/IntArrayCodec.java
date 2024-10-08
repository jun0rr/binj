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
public class IntArrayCodec extends AbstractBinCodec<int[]> {
  
  public IntArrayCodec() {
    super(DefaultBinType.INT_ARRAY);
  }
  
  @Override
  public int[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getInt();
    int[] array = new int[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getInt();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, int[] array) {
    buf.putLong(bintype().id());
    buf.putInt(array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putInt(array[i]);
    }
  }

  @Override
  public int calcSize(int[] array) {
    return Long.BYTES + Integer.BYTES + Integer.BYTES * array.length;
  }

}
