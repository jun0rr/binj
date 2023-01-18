/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.codec;

import com.jun0rr.jbon.BinCodec;
import com.jun0rr.jbon.BinType;
import com.jun0rr.jbon.UnexpectedBinTypeException;
import com.jun0rr.jbon.impl.DefaultBinType;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public class ByteCodec implements BinCodec<Byte> {
  
  @Override
  public BinType<Byte> bintype() {
    return DefaultBinType.BYTE;
  }

  @Override
  public Byte read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    return buf.get();
  }
  
  @Override
  public void write(ByteBuffer buf, Byte val) {
    buf.putLong(bintype().id());
    buf.put(val.byteValue());
  }

  @Override
  public int calcSize(Byte val) {
    return Long.BYTES + Byte.BYTES;
  }

}
