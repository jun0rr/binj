/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class FieldSetStrategy extends AbstractInvokeStrategy<InjectFunction> {

  @Override
  public List<InjectFunction> invokers(Class cls) {
    List<InjectFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = List.of(cls.getDeclaredFields()).stream()
          .filter(f->!Modifier.isFinal(f.getModifiers()))
          .filter(f->!Modifier.isTransient(f.getModifiers()))
          .filter(f->Modifier.isPublic(f.getModifiers()))
          .map(InjectFunction::of)
          .collect(Collectors.toList());
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
