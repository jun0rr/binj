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
  public ZonedDateTime read(ByteBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnexpectedBinTypeException(id);
    }
    ZoneId zid = ZoneOffset.ofTotalSeconds(buf.getInt());
    return Instant.ofEpochMilli(buf.getLong()).atZone(zid);
  }
  
  @Override
  public void write(ByteBuffer buf, ZonedDateTime val) {
    buf.putLong(bintype().id());
    buf.putInt(val.getOffset().getTotalSeconds());
    buf.putLong(val.toInstant().toEpochMilli());
  }

  @Override
  public int calcSize(ZonedDateTime val) {
    return Long.BYTES * 2 + Integer.BYTES;
  }

}
