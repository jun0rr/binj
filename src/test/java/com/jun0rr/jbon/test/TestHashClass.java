/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.test;

import com.jun0rr.jbon.BinType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestHashClass {
  
  @Test
  public void test() {
    Class cls = Long.class;
    System.out.printf("* %s -> %d%n", cls.getCanonicalName(), BinType.genId(cls));
    cls = Map.class;
    System.out.printf("* %s -> %d%n", cls.getCanonicalName(), BinType.genId(cls));
    cls = HashMap.class;
    System.out.printf("* %s -> %d%n", cls.getCanonicalName(), BinType.genId(cls));
    cls = List.class;
    System.out.printf("* %s -> %d%n", cls.getCanonicalName(), BinType.genId(cls));
    cls = LinkedList.class;
    System.out.printf("* %s -> %d%n", cls.getCanonicalName(), BinType.genId(cls));
  }
  
}
