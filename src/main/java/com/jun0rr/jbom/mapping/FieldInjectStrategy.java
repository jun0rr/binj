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
public class FieldInjectStrategy implements InjectStrategy {

  @Override
  public List<InjectFunction> injectors(Class cls) {
    return List.of(cls.getDeclaredFields()).stream()
        .filter(f->!Modifier.isFinal(f.getModifiers()))
        .filter(f->!Modifier.isTransient(f.getModifiers()))
        .map(InjectFunction::of)
        .collect(Collectors.toList());
  }
  
}
