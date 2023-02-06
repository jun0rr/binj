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
public class IntArrayCodec implements BinCodec<int[]> {
  
  @Override
  public BinType<int[]> bintype() {
    return DefaultBinType.INT_ARRAY;
  }

  @Override
  public int[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    int[] array = new int[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getInt();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, int[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putInt(array[i]);
    }
  }

  @Override
  public int calcSize(int[] array) {
    return Long.BYTES + Short.BYTES + Integer.BYTES * array.length;
  }

}
