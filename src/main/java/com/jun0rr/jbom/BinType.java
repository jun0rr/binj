/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

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
  
  
  public static long genId(Class cls) {
    //try {
      //MessageDigest md = MessageDigest.getInstance("SHA-1");
      //md.update(cls.getPackageName().getBytes(StandardCharsets.UTF_8));
      //ByteBuffer buf = ByteBuffer.wrap(md.digest(cls.getCanonicalName().getBytes(StandardCharsets.UTF_8)));
      //long l = buf.getLong();
      //l += buf.getLong();
      //l -= buf.getInt();
      //return l;
      CRC32 crc = new CRC32();
      crc.update(StandardCharsets.UTF_8.encode(cls.getPackageName()));
      crc.update(StandardCharsets.UTF_8.encode(cls.getCanonicalName()));
      return crc.getValue();
    //}
    //catch(NoSuchAlgorithmException e) {
      //return -1L;
    //}
  }
  
}
