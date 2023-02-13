/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.ContextEvent;
import com.jun0rr.binj.mapping.Binary;
import com.jun0rr.binj.mapping.MapConstructor;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import com.jun0rr.binj.ContextListener;
import com.jun0rr.binj.type.MappedInvocationHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author F6036477
 */
public class TestMappedInvocationHandler implements ContextListener {
  
  
  @Test
  public void test() {
    try {
      Map<String,Object> map = new HashMap<>();
      map.put("name", "Hello");
      map.put("last", "World");
      map.put("birth", LocalDate.now());
      map.put("ids", new long[]{Long.MIN_VALUE, Long.MAX_VALUE});
      Person p = MappedInvocationHandler.mapped(Person.class, map);
      Assertions.assertEquals(map.get("name"), p.name());
      Assertions.assertEquals(map.get("last"), p.last());
      Assertions.assertEquals(map.get("birth"), p.birth());
      Assertions.assertArrayEquals((long[])map.get("ids"), p.ids());
      Assertions.assertEquals(null, p.address());
    } 
    catch(Throwable th) {
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
  
  
  
  public static interface Person {
    
    @Binary
    public String name();
    
    @Binary
    public String last();

    @Binary
    public LocalDate birth();

    @Binary
    public long[] ids();

    @Binary
    public Address address();
    
  }
  
  
  
  public static interface Address {
    
    @Binary
    public String street();
    
    @Binary
    public String city();
    
    @Binary
    public int number();
    
  }

  
  
  public static class DefaultPerson implements Person {
    
    private final String name;
    
    private final String last;
    
    private final LocalDate birth;
    
    private final Address address;
    
    private final long[] ids;
    
    //@MapConstructor({"name", "last", "birth", "address", "ids"})
    public DefaultPerson(String name, String last, LocalDate birth, Address address, long[] ids) {
      this.name = Objects.requireNonNull(name);
      this.last = Objects.requireNonNull(last);
      this.birth = Objects.requireNonNull(birth);
      this.address = Objects.requireNonNull(address);
      this.ids = ids;
    }

    @Override
    public String name() {
      return name;
    }
    
    @Override
    public String last() {
      return last;
    }

    @Override
    public LocalDate birth() {
      return birth;
    }

    @Override
    public long[] ids() {
      return ids;
    }

    @Override
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
      final DefaultPerson other = (DefaultPerson) obj;
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
  
  
  public static class DefaultAddress implements Address {
    
    private final String street;
    
    private final String city;
    
    private final int number;
    
    @MapConstructor({"street", "city", "number"})
    public DefaultAddress(String street, String city, int number) {
      this.street = Objects.requireNonNull(street);
      this.city = Objects.requireNonNull(city);
      this.number = number;
    }
    
    @Override
    public String street() {
      return street;
    }
    
    @Override
    public String city() {
      return city;
    }
    
    @Override
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
      final DefaultAddress other = (DefaultAddress) obj;
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
