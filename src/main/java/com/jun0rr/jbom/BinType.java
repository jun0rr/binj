/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import com.jun0rr.jbom.impl.DefaultBinType;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

/**
 *
 * @author F6036477
 */
public interface BinType<T> {
  
  public boolean isTypeOf(Class cls);
  
  public boolean isTypeOf(long id);
  
  public Class<T> type();
  
  public long id();
  
  
  public static <U> BinType<U> of(Class<U> cl, long id) {
    return new DefaultBinType(id, cl);
  }
  
  public static <U> BinType<U> of(Class<U> cl) {
    return new DefaultBinType(cl);
  }
  
  
  public static long genId(Class cls) {
      CRC32 crc = new CRC32();
      crc.update(StandardCharsets.UTF_8.encode(cls.getPackageName()));
      crc.update(StandardCharsets.UTF_8.encode(cls.getCanonicalName()));
      return crc.getValue();
  }
  
}
