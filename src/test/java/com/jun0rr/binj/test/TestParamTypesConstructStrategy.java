/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.mapping.ConstructFunction;
import com.jun0rr.binj.mapping.InvokeStrategy;
import com.jun0rr.binj.mapping.MappingException;
import com.jun0rr.binj.mapping.ParamTypesConstructStrategy;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestParamTypesConstructStrategy {
  
  @Test 
  public void test() {
    InvokeStrategy is = new ParamTypesConstructStrategy();
    List<ConstructFunction> cs = is.invokers(Person.class);
    String name = "John";
    String last = "Doe";
    LocalDate birth = LocalDate.now();
    Map<String,Object> map = new LinkedHashMap<>();
    map.put("name", name);
    map.put("last", last);
    map.put("birth", birth);
    map.values().stream()
        .forEach(v->System.out.printf("- %s (%d)%n", v, v.hashCode()));
    Optional<ConstructFunction> cct = cs.stream()
        .filter(c->c.parameters().size() <= map.size())
        .filter(c->c.parameters().stream().allMatch(s->map.values().stream().anyMatch(v->s.equals(v.getClass()))))
        .findFirst();
    ConstructFunction cf = cct.orElseThrow(()->new MappingException("No ConstructFunction found for " + Person.class));
    Person p = cf.create(map);
    System.out.println(p);
    Assertions.assertNotNull(p);
    Assertions.assertEquals(name, p.name());
    Assertions.assertEquals(last, p.last());
    Assertions.assertEquals(birth, p.birth());
  }
  
  
  public static record Person(String name, String last, LocalDate birth) {}
  
}
