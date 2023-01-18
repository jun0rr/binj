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
import java.time.LocalDate;

/**
 *
 * @author F6036477
 */
public class LocalDateCodec implements BinCodec<LocalDate> {
  
  @Override
  public BinType<LocalDate> bintype() {
    return DefaultBinType.DATE;
  }

  @Override
  public LocalDate read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    int y = buf.getShort();
    int m = buf.get();
    int d = buf.get();
    return LocalDate.of(y, m, d);
  }
  
  @Override
  public void write(ByteBuffer buf, LocalDate val) {
    buf.putLong(bintype().id());
    buf.putShort((short)val.getYear())
        .put((byte)val.getMonthValue())
        .put((byte)val.getDayOfMonth());
  }

  @Override
  public int calcSize(LocalDate val) {
    return Long.BYTES + Short.BYTES + Byte.BYTES * 2;
  }

}
