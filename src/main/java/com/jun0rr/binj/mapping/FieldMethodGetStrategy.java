/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class FieldMethodGetStrategy extends AbstractInvokeStrategy<ExtractFunction> {

  @Override
  public List<ExtractFunction> invokers(Class cls) {
    List<ExtractFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = new LinkedList<>();
      Class sup = cls;
      while(sup != null && sup != Object.class) {
        List<Field> fls = List.of(sup.getDeclaredFields()).stream()
            .filter(f->!Modifier.isTransient(f.getModifiers()))
            .filter(f->!Modifier.isStatic(f.getModifiers()))
            .collect(Collectors.toList());
        List.of(sup.getDeclaredMethods()).stream()
            .filter(m->m.getParameterCount() == 0)
            .filter(m->fls.stream().anyMatch(f->
                m.getName().equals(f.getName()) 
                    && m.getReturnType().equals(f.getType())))
            .map(ExtractFunction::of)
            .forEach(fns::add);
        sup = sup.getSuperclass();
      }
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
