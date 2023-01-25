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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 *
 * @author F6036477
 */
public class ZonedDateTimeCodec implements BinCodec<ZonedDateTime> {
  
  @Override
  public BinType<ZonedDateTime> bintype() {
    return DefaultBinType.ZONED_DATE_TIME;
  }

  @Override
  public ZonedDateTime read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    ZoneId zid = ZoneOffset.ofTotalSeconds(buf.getInt());
    return Instant.ofEpochMilli(buf.getLong()).atZone(zid);
  }
  
  @Override
  public void write(BinBuffer buf, ZonedDateTime val) {
    buf.putLong(bintype().id());
    buf.putInt(val.getOffset().getTotalSeconds());
    buf.putLong(val.toInstant().toEpochMilli());
  }

  @Override
  public int calcSize(ZonedDateTime val) {
    return Long.BYTES * 2 + Integer.BYTES;
  }

}
