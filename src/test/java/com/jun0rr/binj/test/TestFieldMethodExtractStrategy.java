/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.mapping.FieldMethodExtractStrategy;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestFieldMethodExtractStrategy {
  
  @Test public void test() {
    FieldMethodExtractStrategy ex = new FieldMethodExtractStrategy();
    System.out.println("--- Person:");
    Person p = new Person("Hello", "World", LocalDate.now(), 1L);
    ex.invokers(Person.class).stream()
        .peek(f->System.out.println(f))
        .map(f->f.extract(p))
        .forEach(System.out::println);
    System.out.println();
    
    System.out.println("--- PersonR:");
    PersonR r = new PersonR("Hello", "World", LocalDate.now(), 1L);
    ex.invokers(PersonR.class).stream()
        .peek(f->System.out.println(f))
        .map(f->f.extract(r))
        .forEach(System.out::println);
  }
  
  
  public static record PersonR(String name, String last, LocalDate birth, long id) {
    
  }
  
  
  public static class Person {
    
    private final String name;
    
    private final String last;
    
    private final LocalDate birth;
    
    private long id;

    public Person(String name, String last, LocalDate birth, long id) {
      this.name = name;
      this.last = last;
      this.birth = birth;
      this.id = id;
    }

    public String name() {
      return name;
    }
    
    public String last() {
      return last;
    }

    public LocalDate birth() {
      return birth;
    }

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
