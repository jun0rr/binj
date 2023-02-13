/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.UnknownBinTypeException;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinType;
import java.time.LocalDate;

/**
 *
 * @author F6036477
 */
public class LocalDateCodec extends AbstractBinCodec<LocalDate> {
  
  public LocalDateCodec() {
    super(DefaultBinType.DATE);
  }
  
  @Override
  public LocalDate read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int y = buf.getShort();
    int m = buf.get();
    int d = buf.get();
    return LocalDate.of(y, m, d);
  }
  
  @Override
  public void write(BinBuffer buf, LocalDate val) {
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
