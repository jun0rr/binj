/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class DefaultConstructFunction implements ConstructFunction {
  
  private final MethodHandle handle;
  
  private final List<String> arguments;
  
  public DefaultConstructFunction(MethodHandle mh, String... args) {
    this.handle = Objects.requireNonNull(mh);
    this.arguments = (args == null ? Collections.EMPTY_LIST : List.of(args));
  }
  
  public DefaultConstructFunction(MethodHandle mh, List<String> args) {
    this.handle = Objects.requireNonNull(mh);
    this.arguments = Objects.requireNonNull(args);
  }
  
  @Override
  public List<String> arguments() {
    return arguments;
  }
  
  @Override
  public <T> T create(Map<String,Object> map) {
    try {
      List<Object> args = arguments.stream()
          .map(a->map.get(a))
          .collect(Collectors.toList());
      return (T) handle.invokeWithArguments(args);
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  public <T> T create() {
    try {
      return (T) handle.invoke();
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  public static DefaultConstructFunction ofAnnotated(Constructor c) {
    try {
      MapConstructor mc = c.getDeclaredAnnotation(MapConstructor.class);
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c), (mc != null ? mc.value() : null));
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }

  public static DefaultConstructFunction ofParameters(Constructor c) {
    try {
      List<String> args = List.of(c.getParameters()).stream()
          .map(p->p.getName())
          .collect(Collectors.toList());
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c), args);
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }

  @Override
  public String toString() {
    return "ConstructFunction{" + "handle=" + handle + ", arguments=" + arguments + '}';
  }
  
}
