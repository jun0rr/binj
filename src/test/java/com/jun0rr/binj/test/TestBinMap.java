/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.impl.DefaultBinContext;
import com.jun0rr.binj.impl.Pair;
import com.jun0rr.binj.mapping.ObjectMapper;
import com.jun0rr.binj.type.BinMap;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestBinMap {
  
  private static final List<Integer> keys = List.of(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95);
  
  private static final List<String> values = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "[", "\\", "]", "^", "_");
  
  private static final int FROM_CHAR = 0x41;
  
  private static final int LENGTH = 31;
  
  private static final BinMap<Integer,String> map = createBinMap(FROM_CHAR, LENGTH);
  
  public static BinMap<Integer,String> createBinMap(int _char, int length) {
    Map<Integer,String> map = new HashMap<>();
    char c = (char)_char;
    for(int i = 0; i < length; i++) {
      map.put((int)c, Character.toString(c++));
    }
    BinContext ctx = new DefaultBinContext(new ObjectMapper());
    ByteBuffer buf = ByteBuffer.allocate(ctx.calcSize(map));
    ctx.write(buf, map);
    buf.flip();
    return new BinMap(ctx, buf);
  }
  
  @Test
  public void testSize() {
    Assertions.assertEquals(LENGTH, map.size());
  }
  
  @Test
  public void testIsEmpty() {
    Assertions.assertEquals(false, map.isEmpty());
  }
  
  @Test
  public void testConteinsKey() {
    Assertions.assertEquals(true, map.containsKey(0x43));
  }
  
  @Test
  public void testConteinsValue() {
    Assertions.assertEquals(true, map.containsValue("C"));
  }
  
  @Test
  public void testGet() {
    Assertions.assertEquals("C", map.get(0x43));
  }
  
  @Test
  public void testPut() {
    try {
      map.put(0x65, "a");
    }
    catch(Exception e) {
      Assertions.assertEquals(UnsupportedOperationException.class, e.getClass());
      Assertions.assertEquals("Read-Only binary Map", e.getMessage());
    }
  }
  
  @Test
  public void testStaticPut() {
    try {
      BinMap bm = BinMap.put(map, 64, "*");
      System.out.println(bm.printContent());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testKeySet() {
    Set<Integer> set = new TreeSet<>(keys);
    Assertions.assertEquals(set, map.keySet());
  }
  
  @Test
  public void testStreamKeys() {
    Set<Integer> set = new TreeSet<>(keys);
    Assertions.assertTrue(map.streamKeys().allMatch(k->set.stream().anyMatch(j->k.equals(j))));
  }
  
  @Test
  public void testValues() {
    Assertions.assertEquals(values, map.values());
  }
  
  @Test
  public void testStreamValues() {
    Assertions.assertTrue(map.streamValues().allMatch(v->values.stream().anyMatch(u->v.equals(u))));
  }
  
  @Test
  public void testStreamEntries() {
    List<Pair<Integer,String>> entries = new ArrayList<>(keys.size());
    for(int i = 0; i < keys.size(); i++) {
      entries.add(Pair.of(keys.get(i), values.get(i)));
    }
    Assertions.assertTrue(map.streamEntries().allMatch(v->entries.stream().anyMatch(u->v.equals(u))));
  }
  
}
