/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class NoArgsConstructStrategy implements ConstructStrategy {

  @Override
  public List<ConstructFunction> constructors(Class cls) {
    return List.of(cls.getDeclaredConstructors()).stream()
        .filter(c->c.getParameterCount() == 0)
        .map(DefaultConstructFunction::ofAnnotated)
        .collect(Collectors.toList());
  }
  
}
