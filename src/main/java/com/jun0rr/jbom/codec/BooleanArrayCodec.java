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
public class BooleanArrayCodec implements BinCodec<boolean[]> {
  
  public BooleanArrayCodec() {}
  
  @Override
  public BinType<boolean[]> bintype() {
    return DefaultBinType.BOOLEAN_ARRAY;
  }

  @Override
  public boolean[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    boolean[] array = new boolean[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.get() == 1;
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, boolean[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    for(int i = 0; i < array.length; i++) {
      buf.put((byte)(array[i] ? 1 : 0));
    }
  }

  @Override
  public int calcSize(boolean[] array) {
    return Long.BYTES + Short.BYTES + array.length;
  }

}
