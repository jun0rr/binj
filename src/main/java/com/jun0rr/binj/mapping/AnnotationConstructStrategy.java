/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class AnnotationConstructStrategy extends AbstractInvokeStrategy<ConstructFunction> {

  @Override
  public List<ConstructFunction> invokers(Class cls) {
    List<ConstructFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = List.of(cls.getDeclaredConstructors()).stream()
          .filter(c->c.isAnnotationPresent(MapConstructor.class) || c.getParameterCount() == 0)
          .map(DefaultConstructFunction::ofAnnotated)
          .collect(Collectors.toList());
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
