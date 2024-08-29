/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.buffer.BinBuffer;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestCombinedStrategyMapperContext {
  
  @Test public void test() {
    System.out.println("-----------------------------------------------------");
    BinContext ctx = BinContext.ofCombinedStrategyMapper();
    BinBuffer buf = BinBuffer.ofDirectAllocator(128);
    Person p = new Person("John", "Doe", LocalDate.now(), new Address("Hello Street", "World City", 5005));
    System.out.println(p);
    System.out.println(ctx.write(buf, p));
    System.out.println(buf);
    p = ctx.read(buf.flip());
    System.out.println(p);
    Assertions.assertEquals("John", p.name());
    Assertions.assertEquals("Doe", p.last());
    Assertions.assertEquals("Hello Street", p.address().street());
    Assertions.assertEquals("World City", p.address().city());
    Assertions.assertEquals(5005, p.address().number());
    System.out.println("-----------------------------------------------------");
  }
  
  public static record Person(String name, String last, LocalDate birth, Address address) {}
  
  public static record Address(String street, String city, int number) {}
  
}
