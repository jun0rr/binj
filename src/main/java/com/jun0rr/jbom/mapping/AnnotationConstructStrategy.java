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
public class AnnotationConstructStrategy implements ConstructStrategy {

  @Override
  public List<ConstructFunction> constructors(Class cls) {
    return List.of(cls.getDeclaredConstructors()).stream()
        .filter(c->c.isAnnotationPresent(MapConstructor.class) || c.getParameterCount() == 0)
        .map(DefaultConstructFunction::of)
        .collect(Collectors.toList());
  }
  
}
