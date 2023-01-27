/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import com.jun0rr.jbom.mapping.GetterStrategy;
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
public class TestGetterStrategy {
  
  
  @Test
  public void test() {
    Person p = new Person("Hello", "World", LocalDate.of(1980, 7, 7), 99800000000L);
    ObjectMapper mp = new ObjectMapper();
    GetterStrategy gs = new GetterStrategy();
    mp.extractStrategy().add(gs);
    Map<String,Object> map = mp.map(p);
    gs.extractors(p.getClass()).stream()
        .forEach(e->Assertions.assertEquals(e.extract(p), map.get(e.name())));
  }
  
  
  
  public static class Person {
    
    private String name;
    
    private String last;
    
    private LocalDate birth;
    
    private long id;

    public Person() {
    }

    public String getName() {
      return name;
    }

    public Person(String name, String last, LocalDate birth, long id) {
      this.name = name;
      this.last = last;
      this.birth = birth;
      this.id = id;
    }

    public String getLast() {
      return last;
    }

    public LocalDate getBirth() {
      return birth;
    }

    public long getId() {
      return id;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setLast(String last) {
      this.last = last;
    }

    public void setBirth(LocalDate birth) {
      this.birth = birth;
    }

    public void setId(long id) {
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
