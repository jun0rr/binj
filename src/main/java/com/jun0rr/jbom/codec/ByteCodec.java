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
public class ByteCodec implements BinCodec<Byte> {
  
  @Override
  public BinType<Byte> bintype() {
    return DefaultBinType.BYTE;
  }

  @Override
  public Byte read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.get();
  }
  
  @Override
  public void write(BinBuffer buf, Byte val) {
    buf.putLong(bintype().id());
    buf.put(val.byteValue());
  }

  @Override
  public int calcSize(Byte val) {
    return Long.BYTES + Byte.BYTES;
  }

}
