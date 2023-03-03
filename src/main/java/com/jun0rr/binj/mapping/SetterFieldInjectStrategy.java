/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class SetterFieldInjectStrategy extends AbstractInvokeStrategy<InjectFunction> {

  @Override
  public List<InjectFunction> invokers(Class cls) {
    List<InjectFunction> fns = cache.get(cls);
    if(fns == null) {
      List<Field> fls = List.of(cls.getDeclaredFields()).stream()
          .filter(f->!Modifier.isTransient(f.getModifiers()))
          .filter(f->!Modifier.isStatic(f.getModifiers()))
          .collect(Collectors.toList());
      fns = List.of(cls.getDeclaredMethods()).stream()
          .filter(m->m.getParameterCount() == 1)
          .filter(m->fls.stream().anyMatch(f->
              m.getName().equals(f.getName()) 
                  && m.getParameterTypes()[0].equals(f.getType())))
          .map(InjectFunction::of)
          .collect(Collectors.toList());
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
