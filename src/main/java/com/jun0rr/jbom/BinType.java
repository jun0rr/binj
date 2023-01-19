/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author F6036477
 */
public interface BinType<V> {
  
  public long id();
  
  public Class<V> type();
  
  
  public static long genId(Class cls) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      md.update(cls.getPackageName().getBytes(StandardCharsets.UTF_8));
      ByteBuffer buf = ByteBuffer.wrap(md.digest(cls.getCanonicalName().getBytes(StandardCharsets.UTF_8)));
      long l = buf.getLong();
      l += buf.getLong();
      l -= buf.getInt();
      return l;
    }
    catch(NoSuchAlgorithmException e) {
      return -1L;
    }
  }
  
}
