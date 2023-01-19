/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.impl.DefaultBinType;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public class CharCodec implements BinCodec<Character> {
  
  @Override
  public BinType<Character> bintype() {
    return DefaultBinType.CHAR;
  }

  @Override
  public Character read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return (char)buf.get();
  }
  
  @Override
  public void write(ByteBuffer buf, Character val) {
    buf.putLong(bintype().id());
    buf.put((byte)val.charValue());
  }
  
  @Override
  public int calcSize(Character val) {
    return Long.BYTES + Byte.BYTES;
  }

}
