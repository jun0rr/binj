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
public class CharArrayCodec implements BinCodec<char[]> {
  
  @Override
  public BinType<char[]> bintype() {
    return DefaultBinType.CHAR_ARRAY;
  }

  @Override
  public char[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    char[] array = new char[size];
    for(int i = 0; i < size; i++) {
      array[i] = buf.getChar();
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, char[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    for(int i = 0; i < array.length; i++) {
      buf.putChar(array[i]);
    }
  }

  @Override
  public int calcSize(char[] array) {
    return Long.BYTES + Short.BYTES + Character.BYTES * array.length;
  }

}
