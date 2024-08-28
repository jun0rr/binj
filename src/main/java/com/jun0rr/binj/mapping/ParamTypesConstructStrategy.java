/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Stream;


/**
 *
 * @author Juno
 */
public class ParamTypesConstructStrategy extends AbstractInvokeStrategy<ConstructFunction> {

  @Override
  public List<ConstructFunction> invokers(Class cls) {
    List<ConstructFunction> fns = cache.get(cls);
    if(fns == null) {
    fns = Stream.of(cls.getDeclaredConstructors())
        .filter(c->Modifier.isPublic(c.getModifiers()))
        .sorted((a,b)->Integer.compare(a.getParameterCount(), b.getParameterCount())*-1)
        .map(c->ConstructFunction.ofParameterTypes(c, List.of(c.getParameterTypes())))
        .toList();
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
