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
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author F6036477
 */
public class Utf8Codec implements BinCodec<String> {
  
  @Override
  public BinType<String> bintype() {
    return DefaultBinType.UTF8;
  }

  @Override
  public String read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int lim = buf.limit();
    try {
      int len = buf.getShort();
      ByteBuffer bf = ByteBuffer.allocate(len);
      buf.get(bf);
      return StandardCharsets.UTF_8.decode(bf.flip()).toString();
    }
    finally {
      buf.limit(lim);
    }
  }
  
  @Override
  public void write(BinBuffer buf, String val) {
    buf.putLong(bintype().id());
    buf.putShort((short)val.length());
    buf.put(StandardCharsets.UTF_8.encode(val));
  }

  @Override
  public int calcSize(String val) {
    return Long.BYTES + Short.BYTES + val.length();
  }

}
