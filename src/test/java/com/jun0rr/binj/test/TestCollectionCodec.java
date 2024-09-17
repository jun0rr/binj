/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.codec.CollectionCodec;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestCollectionCodec {
  
  @Test public void test() {
    List<String> ls = new LinkedList<>();
    for(int i = 0; i < 100; i++) {
      ls.add(String.format("Hello-%d", i*10));
    }
    BinContext ctx = BinContext.ofCombinedStrategyMapper();
    BinBuffer buf = BinBuffer.ofHeapAllocator(64);
    CollectionCodec codec = new CollectionCodec(ctx);
    codec.write(buf, ls);
    buf.clear();
    ls = new LinkedList<>(codec.read(buf));
    for(int i = 0; i < 100; i++) {
      Assertions.assertEquals(String.format("Hello-%d", i*10), ls.get(i));
    }
  }
  
}
