/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.ContextObserver;
import com.jun0rr.jbom.WriteEvent;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.mapping.AnnotationConstructStrategy;
import com.jun0rr.jbom.mapping.AnnotationExtractStrategy;
import com.jun0rr.jbom.mapping.Binary;
import com.jun0rr.jbom.mapping.MapConstructor;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestNestedObjects implements ContextObserver {
  
  
  @Test
  public void test() {
    try {
      Person p = new Person("Hello", "World", LocalDate.of(1980, 7, 7), 99800000000L, new Address("Bitwise Street", "Byte City", 1024));
      System.out.println(p);
      BinContext ctx = BinContext.newContext();
      ctx.mapper().constructStrategy().add(new AnnotationConstructStrategy());
      ctx.mapper().extractStrategy().add(new AnnotationExtractStrategy());
      ctx.observers().add(this);
      BinBuffer buf = BinBuffer.ofHeapAllocator(128);
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
  public void write(WriteEvent e) {
    System.out.printf("write( %s )%n", e);
  }

  @Override
  public void read(BinType t) {
    System.out.printf("read( %s )%n", t);
  }

  
  
  public static class Person {
    
    private final String name;
    
    private final String last;
    
    private final LocalDate birth;
    
    private final long id;
    
    private final Address address;

    @MapConstructor({"name", "last", "birth", "id", "address"})
    public Person(String name, String last, LocalDate birth, long id, Address address) {
      this.name = Objects.requireNonNull(name);
      this.last = Objects.requireNonNull(last);
      this.birth = Objects.requireNonNull(birth);
      this.address = Objects.requireNonNull(address);
      this.id = id;
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
    public long id() {
      return id;
    }

    @Binary
    public Address address() {
      return address;
    }

    @Override
    public int hashCode() {
      int hash = 3;
      hash = 71 * hash + Objects.hashCode(this.name);
      hash = 71 * hash + Objects.hashCode(this.last);
      hash = 71 * hash + Objects.hashCode(this.birth);
      hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
      hash = 71 * hash + Objects.hashCode(this.address);
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
      if (this.id != other.id) {
        return false;
      }
      if (!Objects.equals(this.name, other.name)) {
        return false;
      }
      if (!Objects.equals(this.last, other.last)) {
        return false;
      }
      if (!Objects.equals(this.birth, other.birth)) {
        return false;
      }
      return Objects.equals(this.address, other.address);
    }

    @Override
    public String toString() {
      return "Person{" + "name=" + name + ", last=" + last + ", birth=" + birth + ", id=" + id + ", address=" + address + '}';
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
