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
public class ShortArrayCodec extends AbstractBinCodec<short[]> {
  
  public ShortArrayCodec() {
    super(DefaultBinType.SHORT_ARRAY);
  }
  
  @Override
  public short[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getInt();
    short[] array = new short[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getShort();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, short[] array) {
    buf.putLong(bintype().id());
    buf.putInt(array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putShort(array[i]);
    }
  }

  @Override
  public int calcSize(short[] array) {
    return Long.BYTES + Integer.BYTES + Short.BYTES * array.length;
  }

}
