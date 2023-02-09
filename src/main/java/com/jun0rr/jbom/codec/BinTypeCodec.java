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
public class BinTypeCodec implements BinCodec<BinType> {
  
  @Override
  public BinType<BinType> bintype() {
    return DefaultBinType.BINTYPE;
  }

  @Override
  public BinType read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    id = buf.getLong();
    try {
      Class c = Class.forName(buf.getUTF8());
      return new DefaultBinType(id, c);
    }
    catch(ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void write(BinBuffer buf, BinType val) {
    buf.putLong(bintype().id());
    buf.putLong(val.id());
    String cname = val.type().getCanonicalName();
    buf.putUTF8(cname);
  }

  @Override
  public int calcSize(BinType val) {
    return Long.BYTES * 2 + Short.BYTES + val.type().getCanonicalName().length();
  }
  
}
