/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.codec.ClassCodec;
import java.nio.ByteBuffer;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestClassCodec {
  
  @Test
  public void test() {
    ClassCodec cc = new ClassCodec();
    BinBuffer buf = BinBuffer.ofDirectAllocator(64);
    cc.write(buf, String.class);
    cc.write(buf, ByteBuffer.class);
    cc.write(buf, List.class);
    cc.write(buf, this.getClass());
    System.out.println(buf);
    buf.flip();
    System.out.println(cc.read(buf));
    System.out.println(cc.read(buf));
    System.out.println(cc.read(buf));
    System.out.println(cc.read(buf));
  }
  
}
