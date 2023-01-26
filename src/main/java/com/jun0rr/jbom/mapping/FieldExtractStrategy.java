/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class FieldExtractStrategy implements ExtractStrategy {

  @Override
  public List<ExtractFunction> extractors(Class cls) {
    return List.of(cls.getDeclaredFields()).stream()
        .filter(f->!Modifier.isTransient(f.getModifiers()))
        .map(ExtractFunction::of)
        .collect(Collectors.toList());
  }
  
}
