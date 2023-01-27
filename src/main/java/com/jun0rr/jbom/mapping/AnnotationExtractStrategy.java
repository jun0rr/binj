/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author F6036477
 */
public class AnnotationExtractStrategy implements ExtractStrategy {

  @Override
  public List<ExtractFunction> extractors(Class cls) {
    return Stream.concat(
        List.of(cls.getDeclaredFields()).stream()
            .filter(f->f.isAnnotationPresent(Binary.class))
            .map(DefaultExtractFunction::of),
        List.of(cls.getDeclaredMethods()).stream()
            .filter(m->m.isAnnotationPresent(Binary.class))
            .filter(m->m.getParameterCount() == 0)
            .filter(m->m.getReturnType() != void.class)
            .map(DefaultExtractFunction::of)
    ).collect(Collectors.toList());
  }
  
}
