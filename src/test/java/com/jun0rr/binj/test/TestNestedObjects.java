/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.ContextEvent;
import com.jun0rr.binj.ContextListener;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.mapping.AnnotationConstructStrategy;
import com.jun0rr.binj.mapping.AnnotationGetStrategy;
import com.jun0rr.binj.mapping.Binary;
import com.jun0rr.binj.mapping.DefaultConstructStrategy;
import com.jun0rr.binj.mapping.MapConstructor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestNestedObjects implements ContextListener {
  
  
  @Test
  public void test() {
    try {
      Person p = new Person("Hello", "World", LocalDate.of(1980, 7, 7), new Address("Bitwise Street", "Byte City", 1024), new long[]{(long)(Math.random() * Short.MAX_VALUE), (long)(Math.random() * Short.MAX_VALUE)});
      System.out.println(p);
      BinContext ctx = BinContext.newContext();
      ctx.mapper().constructStrategies()
          .put(1, new DefaultConstructStrategy())
          .put(2, new AnnotationConstructStrategy());
      ctx.mapper().extractStrategies().put(1, new AnnotationGetStrategy());
      ctx.listeners().add(this);
      BinBuffer buf = BinBuffer.ofHeapAllocator(128);
      System.out.println(buf);
      System.out.println(ctx.calcSize(p));
      ctx.write(buf, p);
      buf.flip();
      Person q = ctx.read(buf);
      System.out.println(q);
      Assertions.assertEquals(p, q);
    } catch(Throwable th) {
      th.printStackTrace();
      throw th;
    }
  }

  @Override
  public void write(ContextEvent e) {
    System.out.printf("write( %s )%n", e);
  }

  @Override
  public void read(ContextEvent e) {
    System.out.printf("read( %s )%n", e);
  }

  
  
  public static class Person {
    
    private final String name;
    
    private final String last;
    
    private final LocalDate birth;
    
    private final Address address;
    
    private final long[] ids;
    
    @MapConstructor({"name", "last", "birth", "address", "ids"})
    public Person(String name, String last, LocalDate birth, Address address, long[] ids) {
      this.name = Objects.requireNonNull(name);
      this.last = Objects.requireNonNull(last);
      this.birth = Objects.requireNonNull(birth);
      this.address = Objects.requireNonNull(address);
      this.ids = ids;
    }

    @Binary
    public String name() {
      return name;
    }
    
    @Binary
    public String last() {
      return last;
    }

    @Binary
    public LocalDate birth() {
      return birth;
    }

    @Binary
    public long[] ids() {
      return ids;
    }

    @Binary
    public Address address() {
      return address;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 89 * hash + Objects.hashCode(this.name);
      hash = 89 * hash + Objects.hashCode(this.last);
      hash = 89 * hash + Objects.hashCode(this.birth);
      hash = 89 * hash + Objects.hashCode(this.address);
      hash = 89 * hash + Arrays.hashCode(this.ids);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Person other = (Person) obj;
      if (!Objects.equals(this.name, other.name)) {
        return false;
      }
      if (!Objects.equals(this.last, other.last)) {
        return false;
      }
      if (!Objects.equals(this.birth, other.birth)) {
        return false;
      }
      if (!Objects.equals(this.address, other.address)) {
        return false;
      }
      return Arrays.equals(this.ids, other.ids);
    }
    
    @Override
    public String toString() {
      return "Person{" + "name=" + name + ", last=" + last + ", birth=" + birth + ", address=" + address + ", ids=" + Arrays.toString(ids) + '}';
    }
    
  }
  
  
  public static class Address {
    
    private final String street;
    
    private final String city;
    
    private final int number;
    
    @MapConstructor({"street", "city", "number"})
    public Address(String street, String city, int number) {
      this.street = Objects.requireNonNull(street);
      this.city = Objects.requireNonNull(city);
      this.number = number;
    }
    
    @Binary
    public String street() {
      return street;
    }
    
    @Binary
    public String city() {
      return city;
    }
    
    @Binary
    public int number() {
      return number;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 29 * hash + Objects.hashCode(this.street);
      hash = 29 * hash + Objects.hashCode(this.city);
      hash = 29 * hash + this.number;
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Address other = (Address) obj;
      if (this.number != other.number) {
        return false;
      }
      if (!Objects.equals(this.street, other.street)) {
        return false;
      }
      return Objects.equals(this.city, other.city);
    }

    @Override
    public String toString() {
      return "Address{" + "street=" + street + ", city=" + city + ", number=" + number + '}';
    }
    
  }
  
}
