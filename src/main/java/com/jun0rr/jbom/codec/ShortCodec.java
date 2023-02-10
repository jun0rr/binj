/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;

/**
 *
 * @author F6036477
 */
public class ShortCodec extends AbstractBinCodec<Short> {
  
  public ShortCodec() {
    super(DefaultBinType.SHORT);
  }
  
  @Override
  public Short read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return buf.getShort();
  }
  
  @Override
  public void write(BinBuffer buf, Short val) {
    buf.putLong(bintype().id());
    buf.putShort(val);
  }

  @Override
  public int calcSize(Short val) {
    return Long.BYTES + Short.BYTES;
  }

}
