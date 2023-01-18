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
  public String read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    int lim = buf.limit();
    try {
      int len = buf.getInt();
      buf.limit(buf.position() + len);
      return StandardCharsets.UTF_8.decode(buf).toString();
    }
    finally {
      buf.limit(lim);
    }
  }
  
  @Override
  public void write(ByteBuffer buf, String val) {
    buf.putLong(bintype().id());
    buf.putInt(val.length());
    buf.put(StandardCharsets.UTF_8.encode(val));
  }

  @Override
  public int calcSize(String val) {
    return Long.BYTES + Integer.BYTES + val.length();
  }

}
