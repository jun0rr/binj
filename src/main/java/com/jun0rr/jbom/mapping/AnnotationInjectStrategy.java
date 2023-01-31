/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author F6036477
 */
public class AnnotationInjectStrategy implements InjectStrategy {

  @Override
  public List<InjectFunction> injectors(Class cls) {
    return Stream.concat(
        List.of(cls.getDeclaredFields()).stream()
            .filter(f->!Modifier.isFinal(f.getModifiers()))
            .filter(f->f.isAnnotationPresent(Binary.class))
            .map(DefaultInjectFunction::of),
        List.of(cls.getDeclaredMethods()).stream()
            .filter(m->m.isAnnotationPresent(Binary.class))
            .filter(m->m.getParameterCount() == 1)
            .map(DefaultInjectFunction::of)
    ).collect(Collectors.toList());
  }
  
}