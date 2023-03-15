/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.mapping.ConstructFunction;
import com.jun0rr.binj.mapping.FieldsOrderConstructStrategy;
import com.jun0rr.binj.mapping.MappingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author F6036477
 */
public class TestFieldsOrderConstructStrategy {
  
  @Test public void test() {
    FieldsOrderConstructStrategy fcs = new FieldsOrderConstructStrategy();
    List<ConstructFunction> cs = fcs.invokers(Person.class);
    String name = "John";
    String last = "Doe";
    LocalDate birth = LocalDate.now();
    Map<String,Object> map = new HashMap<>();
    map.put("name", name);
    map.put("last", last);
    map.put("birth", birth);
    Optional<ConstructFunction> cct = cs.stream()
        .sorted((a,b)->Integer.compare(a.arguments().size(), b.arguments().size()) * -1)
        .filter(c->c.arguments().size() <= map.size())
        .filter(c->c.arguments().stream().allMatch(s->map.keySet().stream().anyMatch(k->s.equals(k))))
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
