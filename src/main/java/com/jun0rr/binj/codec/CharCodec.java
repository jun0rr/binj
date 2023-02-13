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
public class CharCodec extends AbstractBinCodec<Character> {
  
  public CharCodec() {
    super(DefaultBinType.CHAR);
  }
  
  @Override
  public Character read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return (char)buf.get();
  }
  
  @Override
  public void write(BinBuffer buf, Character val) {
    buf.putLong(bintype().id());
    buf.put((byte)val.charValue());
  }
  
  @Override
  public int calcSize(Character val) {
    return Long.BYTES + Byte.BYTES;
  }

}
