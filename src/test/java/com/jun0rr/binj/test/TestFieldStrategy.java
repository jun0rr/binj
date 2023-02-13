/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinContext;
import com.jun0rr.binj.mapping.AnnotationConstructStrategy;
import com.jun0rr.binj.mapping.ConstructStrategy;
import com.jun0rr.binj.mapping.ExtractStrategy;
import com.jun0rr.binj.mapping.FieldExtractStrategy;
import com.jun0rr.binj.mapping.MapConstructor;
import com.jun0rr.binj.mapping.ObjectMapper;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestFieldStrategy {
  
  
  @Test
  public void test() {
    try {
      Person p = new Person("Hello", "World", LocalDate.of(1980, 7, 7), 99800000000L);
      System.out.println(p);
      ConstructStrategy cs = new AnnotationConstructStrategy();
      ExtractStrategy es = new FieldExtractStrategy();
      ObjectMapper mp = new ObjectMapper();
      mp.constructStrategy().add(cs);
      mp.extractStrategy().add(es);
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
    
    public final String name;
    
    public final String last;
    
    public final LocalDate birth;
    
    public final long id;
    
    public transient final Class cls = Person.class;

    @MapConstructor({"name", "last", "birth", "id"})
    public Person(String name, String last, LocalDate birth, long id) {
      this.name = name;
      this.last = last;
      this.birth = birth;
      this.id = id;
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
