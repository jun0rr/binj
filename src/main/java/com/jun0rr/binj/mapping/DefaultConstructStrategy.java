/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 *
 * @author F6036477
 */
public class DefaultConstructStrategy extends AbstractInvokeStrategy<ConstructFunction> {

  @Override
  public List<ConstructFunction> invokers(Class cls) {
    List<ConstructFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = List.of(cls.getDeclaredConstructors()).stream()
          .filter(c->Modifier.isPublic(c.getModifiers()))
          .sorted((a,b)->Integer.compare(a.getParameterCount(), b.getParameterCount())*-1)
          .map(ConstructFunction::ofParameters)
          .peek(c->System.out.printf("* constructor: %s%n", c))
          .toList();
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
