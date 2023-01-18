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
public class ShortCodec implements BinCodec<Short> {
  
  @Override
  public BinType<Short> bintype() {
    return DefaultBinType.SHORT;
  }

  @Override
  public Short read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    return buf.getShort();
  }
  
  @Override
  public void write(ByteBuffer buf, Short val) {
    buf.putLong(bintype().id());
    buf.putShort(val);
  }

  @Override
  public int calcSize(Short val) {
    return Long.BYTES + Short.BYTES;
  }

}
