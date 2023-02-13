/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinType;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.codec.BinTypeCodec;
import com.jun0rr.binj.impl.DefaultBinType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestBinTypeCodec {
  
  @Test
  public void test() {
    BinTypeCodec cd = new BinTypeCodec();
    BinBuffer buf = BinBuffer.ofHeapAllocator(50);
    System.out.println(DefaultBinType.DATE);
    cd.write(buf, DefaultBinType.DATE);
    System.out.println(buf);
    try {
      BinType date = cd.read(buf.flip());
      System.out.println(date);
      Assertions.assertEquals(DefaultBinType.DATE, date);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
