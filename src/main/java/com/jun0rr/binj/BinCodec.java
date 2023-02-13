/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj;

import com.jun0rr.binj.buffer.BinBuffer;
import static com.jun0rr.binj.impl.DefaultBinType.BINTYPE;
import static com.jun0rr.binj.impl.DefaultBinType.BOOLEAN;
import static com.jun0rr.binj.impl.DefaultBinType.BOOLEAN_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.BYTE;
import static com.jun0rr.binj.impl.DefaultBinType.BYTE_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.CHAR;
import static com.jun0rr.binj.impl.DefaultBinType.CHAR_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.CLASS;
import static com.jun0rr.binj.impl.DefaultBinType.COLLECTION;
import static com.jun0rr.binj.impl.DefaultBinType.DATE;
import static com.jun0rr.binj.impl.DefaultBinType.DATE_TIME;
import static com.jun0rr.binj.impl.DefaultBinType.DOUBLE;
import static com.jun0rr.binj.impl.DefaultBinType.DOUBLE_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.ENUM;
import static com.jun0rr.binj.impl.DefaultBinType.FLOAT;
import static com.jun0rr.binj.impl.DefaultBinType.FLOAT_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.IDXKEY;
import static com.jun0rr.binj.impl.DefaultBinType.INSTANT;
import static com.jun0rr.binj.impl.DefaultBinType.INTEGER;
import static com.jun0rr.binj.impl.DefaultBinType.INT_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.LONG;
import static com.jun0rr.binj.impl.DefaultBinType.LONG_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.MAP;
import static com.jun0rr.binj.impl.DefaultBinType.SHORT;
import static com.jun0rr.binj.impl.DefaultBinType.SHORT_ARRAY;
import static com.jun0rr.binj.impl.DefaultBinType.UTF8;
import static com.jun0rr.binj.impl.DefaultBinType.ZONED_DATE_TIME;
import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author F6036477
 */
public interface BinCodec<V> {
  
  public BinType<V> bintype();
  
  public V read(BinBuffer buf);
  
  public void write(BinBuffer buf, V val);
  
  public default V read(ByteBuffer buf) {
    return read(BinBuffer.of(buf));
  }
  
  public default void write(ByteBuffer buf, V val) {
    write(BinBuffer.of(buf), val);
  }
  
  public int calcSize(V val);
  

  public static final List<BinType> DEFAULT_BINTYPES = List.of(
      BINTYPE, BOOLEAN, BOOLEAN_ARRAY, BYTE, BYTE_ARRAY,
      CHAR, CHAR_ARRAY, CLASS, COLLECTION, DATE, DATE_TIME,
      DOUBLE, DOUBLE_ARRAY, ENUM, FLOAT, FLOAT_ARRAY,
      IDXKEY, INSTANT, INTEGER, INT_ARRAY, LONG, LONG_ARRAY,
      MAP, SHORT, SHORT_ARRAY, UTF8, ZONED_DATE_TIME
  );
  
}
