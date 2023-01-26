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
public class SetterInjectStrategy implements InjectStrategy {

  @Override
  public List<InjectFunction> injectors(Class cls) {
    return List.of(cls.getDeclaredMethods()).stream()
        .filter(m->m.getName().startsWith("set"))
        .filter(m->m.getParameterCount() == 1)
        .map(InjectFunction::of)
        .collect(Collectors.toList());
  }
  
}
