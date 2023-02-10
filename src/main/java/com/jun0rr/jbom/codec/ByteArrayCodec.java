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
public class ByteArrayCodec extends AbstractBinCodec<byte[]> {
  
  public ByteArrayCodec() {
    super(DefaultBinType.BYTE_ARRAY);
  }
  
  @Override
  public byte[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    byte[] bs = new byte[size];
    buf.get(bs);
    return bs;
  }
  
  @Override
  public void write(BinBuffer buf, byte[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    buf.put(array);
  }

  @Override
  public int calcSize(byte[] array) {
    return Long.BYTES + Short.BYTES + array.length;
  }

}
