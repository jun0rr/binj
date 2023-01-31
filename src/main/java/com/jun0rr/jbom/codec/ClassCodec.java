/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.BinTypeNotFoundException;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author F6036477
 */
public class ClassCodec implements BinCodec<Class> {
  
  @Override
  public BinType<Class> bintype() {
    return DefaultBinType.CLASS;
  }

  @Override
  public Class read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int lim = buf.limit();
    try {
      int len = buf.getShort();
      ByteBuffer bf = ByteBuffer.allocate(len);
      buf.get(bf);
      return Class.forName(StandardCharsets.UTF_8.decode(bf.flip()).toString());
    }
    catch(ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    finally {
      buf.limit(lim);
    }
  }
  
  @Override
  public void write(BinBuffer buf, Class val) {
    buf.putLong(bintype().id());
    buf.putShort((short)val.getCanonicalName().length());
    buf.put(StandardCharsets.UTF_8.encode(val.getCanonicalName()));
  }

  @Override
  public int calcSize(Class val) {
    return Long.BYTES + Short.BYTES + val.getCanonicalName().length();
  }

}
