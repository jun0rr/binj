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
public class BooleanArrayCodec extends AbstractBinCodec<boolean[]> {
  
  public BooleanArrayCodec() {
    super(DefaultBinType.BOOLEAN_ARRAY);
  }
  
  @Override
  public boolean[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getInt();
    boolean[] array = new boolean[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.get() == 1;
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, boolean[] array) {
    buf.putLong(bintype().id());
    buf.putInt(array.length);
    for(int i = 0; i < array.length; i++) {
      buf.put((byte)(array[i] ? 1 : 0));
    }
  }

  @Override
  public int calcSize(boolean[] array) {
    return Long.BYTES + Integer.BYTES + array.length;
  }

}
