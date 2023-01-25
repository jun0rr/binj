/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.impl.DefaultBinContext;
import com.jun0rr.jbom.type.BinList;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestBinList {
  
  private static final List<Integer> values = List.of(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95);
  
  private static final int FROM_CHAR = 0x41;
  
  private static final int LENGTH = 31;
  
  private static final BinList<Integer> list = createBinList();
  
  public static BinList<Integer> createBinList() {
    try {
      BinContext ctx = new DefaultBinContext();
      ByteBuffer buf = ByteBuffer.allocate(ctx.calcSize(values));
      ctx.write(buf, values);
      buf.flip();
      return new BinList(ctx, buf);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void testSize() {
    Assertions.assertEquals(LENGTH, list.size());
  }
  
  @Test
  public void testIsEmpty() {
    Assertions.assertEquals(false, list.isEmpty());
  }
  
  @Test
  public void testContains() {
    Assertions.assertEquals(true, list.contains(80));
  }
  
  @Test
  public void testIterator() {
    int i = 0;
    Iterator<Integer> it = list.iterator();
    while(it.hasNext()) {
      Object r = it.next();
      if(values.stream().anyMatch(v->v.equals(r))) {
        i++;
      }
    }
    Assertions.assertEquals(LENGTH, i);
  }
  
  @Test
  public void testToArrayObject() {
    Object[] os = list.toArray();
    for(int i = 0; i < os.length; i++) {
      Assertions.assertEquals(os[i], values.get(i));
    }
  }
  
  @Test
  public void testToArray() {
    Integer[] is = new Integer[list.size()];
    is = list.toArray(is);
    for(int i = 0; i < is.length; i++) {
      Assertions.assertEquals(is[i], values.get(i));
    }
  }
  
  @Test
  public void testAdd() {
    try {
      list.add(0x65);
    }
    catch(Exception e) {
      Assertions.assertEquals(UnsupportedOperationException.class, e.getClass());
      Assertions.assertEquals("Read-Only binary List", e.getMessage());
    }
  }
  
  @Test
  public void testContainsAll() {
    Assertions.assertTrue(list.containsAll(values));
  }
  
  @Test
  public void testGet() {
    Assertions.assertEquals(80, list.get(15));
  }
  
  @Test
  public void testIndexOf() {
    Assertions.assertEquals(15, list.indexOf(80));
  }
  
  @Test
  public void testLastIndexOf() {
    Assertions.assertEquals(15, list.indexOf(80));
  }
  
  @Test
  public void testListIterator() {
    int i = 0;
    ListIterator<Integer> it = list.listIterator();
    while(it.hasNext()) {
      Object r = it.next();
      if(values.stream().anyMatch(v->v.equals(r))) {
        i++;
      }
    }
    Assertions.assertEquals(LENGTH, i);
  }
  
  @Test
  public void testListIteratorIndex() {
    int i = 0;
    ListIterator<Integer> it = list.listIterator(15);
    while(it.hasNext()) {
      Object r = it.next();
      if(values.stream().anyMatch(v->v.equals(r))) {
        i++;
      }
    }
    Assertions.assertEquals(LENGTH -15, i);
  }
  
  @Test
  public void testSubList() {
    int i = 0;
    List<Integer> ls = list.subList(15, LENGTH);
    Iterator<Integer> it = ls.iterator();
    while(it.hasNext()) {
      Object r = it.next();
      if(values.stream().anyMatch(v->v.equals(r))) {
        i++;
      }
    }
    Assertions.assertEquals(LENGTH -15, i);
  }
  
}
