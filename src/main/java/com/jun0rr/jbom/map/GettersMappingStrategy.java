/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.map;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class GettersMappingStrategy implements MappingStrategy {

  @Override
  public List<Function> supply(Object o) {
    Objects.requireNonNull(o);
    Class c = o.getClass();
    List.of(c.getDeclaredMethods()).stream()
        .filter(m->m.getName().startsWith("get"))
        .filter(m->m.getReturnType() != void.class)
        .filter(m->m.getParameterCount() == 0)
        .collect(Collectors.toList());
    
    return null;
  }

  @Override
  public List<Function> consume(Object o) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
  
}
