/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author F6036477
 */
public class AnnotationInjectStrategy extends AbstractInvokeStrategy<InjectFunction> {

  @Override
  public List<InjectFunction> invokers(Class cls) {
    List<InjectFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = new LinkedList<>();
      Class sup = cls;
      while(sup != null && sup != Object.class) {
        Stream.concat(
            List.of(cls.getDeclaredFields()).stream()
                .filter(f->!Modifier.isFinal(f.getModifiers()))
                .filter(f->f.isAnnotationPresent(Binary.class))
                .map(DefaultInjectFunction::of),
            List.of(cls.getDeclaredMethods()).stream()
                .filter(m->m.getParameterCount() == 1)
                .filter(m->m.isAnnotationPresent(Binary.class))
                .map(DefaultInjectFunction::of)
        ).forEach(fns::add);
        List.of(cls.getInterfaces()).stream()
            .flatMap(c->List.of(c.getDeclaredMethods()).stream())
            .filter(m->m.getParameterCount() == 1)
            .filter(m->m.isAnnotationPresent(Binary.class))
            .map(DefaultInjectFunction::of)
            .forEach(fns::add);
        sup = sup.getSuperclass();
      }
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
