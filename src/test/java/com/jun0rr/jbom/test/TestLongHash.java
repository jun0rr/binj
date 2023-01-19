/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestLongHash {
  
  @Test
  public void test() throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(HashMap.class.getPackageName().getBytes(StandardCharsets.UTF_8));
    ByteBuffer buf = ByteBuffer.wrap(md.digest(HashMap.class.getCanonicalName().getBytes(StandardCharsets.UTF_8)));
    System.out.println(buf);
    long l1 = buf.getLong();
    long l2 = buf.getLong();
    System.out.printf("* l1=%d, l2=%d, sum=%d%n", l1, l2, l1+l2);
    
    md = MessageDigest.getInstance("SHA-1");
    md.update(HashMap.class.getPackageName().getBytes(StandardCharsets.UTF_8));
    buf = ByteBuffer.wrap(md.digest(HashMap.class.getCanonicalName().getBytes(StandardCharsets.UTF_8)));
    System.out.println(buf);
    l1 = buf.getLong();
    l2 = buf.getLong();
    long l3 = buf.getInt();
    System.out.printf("* l1=%d, l2=%d, l3=%d, sum=%d%n", l1, l2, l3, l1+l2+l3);
  }
  
}
