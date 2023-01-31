/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinContext;
import com.jun0rr.jbom.mapping.AnnotationConstructStrategy;
import com.jun0rr.jbom.mapping.AnnotationExtractStrategy;
import com.jun0rr.jbom.mapping.AnnotationInjectStrategy;
import com.jun0rr.jbom.mapping.Binary;
import com.jun0rr.jbom.mapping.ConstructStrategy;
import com.jun0rr.jbom.mapping.ExtractStrategy;
import com.jun0rr.jbom.mapping.InjectStrategy;
import com.jun0rr.jbom.mapping.MapConstructor;
import com.jun0rr.jbom.mapping.ObjectMapper;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestObjectMapper {
  
  
  @Test
  public void test() {
    try {
      Person p = new Person("Hello", "World", LocalDate.of(1980, 7, 7), 99800000000L);
      System.out.println(p);
      ObjectMapper mp = ObjectMapper.withAnnotationStrategies();
      BinContext ctx = new DefaultBinContext(mp);
      BinBuffer buf = BinBuffer.ofHeapAllocator(128);
      System.out.println(ctx.calcSize(p));
      ctx.write(buf, p);
      System.out.println(buf);
      buf.flip();
      Person q = ctx.read(buf);
      System.out.println(q);
      Assertions.assertEquals(p, q);
    } catch(Throwable th) {
      th.printStackTrace();
      throw th;
    }
  }
  
  
  
  public static class Person {
    
    private final String name;
    
    private final String last;
    
    private final LocalDate birth;
    
    private long id;

    @MapConstructor({"name", "last", "birth", "id"})
    public Person(String name, String last, LocalDate birth, long id) {
      this.name = name;
      this.last = last;
      this.birth = birth;
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

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 97 * hash + Objects.hashCode(this.name);
      hash = 97 * hash + Objects.hashCode(this.last);
      hash = 97 * hash + Objects.hashCode(this.birth);
      hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
      return Objects.equals(this.birth, other.birth);
    }

    @Override
    public String toString() {
      return "Person{" + "name=" + name + ", last=" + last + ", birth=" + birth + ", id=" + id + '}';
    }
    
  }
  
}
