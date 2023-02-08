/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinContext;
import com.jun0rr.jbom.mapping.ObjectMapper;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestEnum {
  
  @Test
  public void test() throws Throwable {
    System.out.println("getClass         : " + Letters.A.getClass());
    System.out.println("getDeclaringClass: " + Letters.A.getDeclaringClass());
    Lookup lo = MethodHandles.publicLookup();
    MethodHandle mh = lo.unreflect(Letters.class.getDeclaredMethod("values"));
    Letters[] la = (Letters[]) mh.invoke();
    System.out.println(Arrays.toString(la));
  }
  
  @Test
  public void testCodec() {
    BinContext ctx = new DefaultBinContext(new ObjectMapper());
    BinBuffer buf = BinBuffer.ofHeapAllocator(20);
    ctx.write(buf, Letters.B);
    Letters l = ctx.read(buf.flip());
    Assertions.assertTrue(Letters.B == l);
  }
  
  public static enum Letters {
    A, B, C, D, E
  }
  
}
