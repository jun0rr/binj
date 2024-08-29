/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.List;

/**
 *
 * @author F6036477
 */
public class NoArgsConstructStrategy extends AbstractInvokeStrategy<ConstructFunction> {

  @Override
  public List<ConstructFunction> invokers(Class cls) {
    List<ConstructFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = List.of(cls.getDeclaredConstructors()).stream()
          .filter(c->c.getParameterCount() == 0)
          .map(ConstructFunction::ofAnnotated)
          //.peek(c->System.out.printf("* NoArgsConstructStrategy.constructor: %s%n", c))
          .toList();
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
