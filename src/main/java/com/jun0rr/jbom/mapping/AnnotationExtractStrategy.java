/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author F6036477
 */
public class AnnotationExtractStrategy implements ExtractStrategy {

  @Override
  public List<ExtractFunction> extractors(Class cls) {
    List<ExtractFunction> fns = new LinkedList<>();
    Class sup = cls;
    while(sup != null && sup != Object.class) {
      Stream.concat(
          List.of(cls.getDeclaredFields()).stream()
              .filter(f->f.isAnnotationPresent(Binary.class))
              .map(DefaultExtractFunction::of),
          List.of(cls.getDeclaredMethods()).stream()
              .filter(m->m.getParameterCount() == 0)
              .filter(m->m.getReturnType() != void.class)
              .filter(m->m.isAnnotationPresent(Binary.class))
              .map(DefaultExtractFunction::of)
      ).forEach(fns::add);
      List.of(cls.getInterfaces()).stream()
          .flatMap(c->List.of(c.getDeclaredMethods()).stream())
          .filter(m->m.getParameterCount() == 0)
          .filter(m->m.getReturnType() != void.class)
          .filter(m->m.isAnnotationPresent(Binary.class))
          .map(DefaultExtractFunction::of)
          .forEach(fns::add);
      sup = sup.getSuperclass();
    }
    return fns;
  }
  
}
