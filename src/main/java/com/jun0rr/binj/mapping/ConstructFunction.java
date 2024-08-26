/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public interface ConstructFunction {
  
  public List arguments();
  
  public List parameters();
  
  public <T> T create(Map<String,Object> map);
  
  public <T> T create();
  
  
  
  public static ConstructFunction ofNoArgs(Constructor c) {
    try {
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c));
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }
  
  public static ConstructFunction ofAnnotated(Constructor c) {
    try {
      MapConstructor mc = c.getDeclaredAnnotation(MapConstructor.class);
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c), (mc != null ? mc.value() : null));
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }

  public static ConstructFunction ofParameters(Constructor c) {
    try {
      List<String> args = List.of(c.getParameters()).stream()
          .map(p->p.getName())
          .collect(Collectors.toList());
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c), args);
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }
  
  public static ConstructFunction ofFields(Constructor c, List<String> fields) {
    try {
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c), fields);
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }

}
