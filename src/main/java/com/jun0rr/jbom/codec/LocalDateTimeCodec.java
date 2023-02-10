/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author F6036477
 */
public class LocalDateTimeCodec extends AbstractBinCodec<LocalDateTime> {
  
  public LocalDateTimeCodec() {
    super(DefaultBinType.DATE_TIME);
  }
  
  @Override
  public LocalDateTime read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(buf.getLong()), ZoneOffset.UTC);
  }
  
  @Override
  public void write(BinBuffer buf, LocalDateTime val) {
    buf.putLong(bintype().id());
    buf.putLong(val.toInstant(ZoneOffset.UTC).toEpochMilli());
  }

  @Override
  public int calcSize(LocalDateTime val) {
    return Long.BYTES * 2;
  }

}
