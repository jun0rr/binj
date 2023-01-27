/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class GetterStrategy implements ExtractStrategy {

  @Override
  public List<ExtractFunction> extractors(Class cls) {
    return List.of(cls.getDeclaredMethods()).stream()
        .filter(m->m.getName().startsWith("get"))
        .filter(m->m.getParameterCount() == 0)
        .filter(m->m.getReturnType() != void.class)
        .map(DefaultExtractFunction::of)
        .collect(Collectors.toList());
  }
  
}
