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
public class CharArrayCodec extends AbstractBinCodec<char[]> {
  
  public CharArrayCodec() {
    super(DefaultBinType.CHAR_ARRAY);
  }
  
  @Override
  public char[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getInt();
    char[] array = new char[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getChar();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, char[] array) {
    buf.putLong(bintype().id());
    buf.putInt(array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putChar(array[i]);
    }
  }

  @Override
  public int calcSize(char[] array) {
    return Long.BYTES + Integer.BYTES + Character.BYTES * array.length;
  }

}
