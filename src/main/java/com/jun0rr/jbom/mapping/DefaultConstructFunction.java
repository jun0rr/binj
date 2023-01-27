/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class DefaultConstructFunction implements ConstructFunction {
  
  private final MethodHandle handle;
  
  private final String[] arguments;
  
  public DefaultConstructFunction(MethodHandle mh, String... args) {
    this.handle = Objects.requireNonNull(mh);
    this.arguments = (args == null ? new String[0] : args);
  }
  
  @Override
  public String[] arguments() {
    return arguments;
  }
  
  @Override
  public <T> T create(Map<String,Object> map) {
    try {
      List<Object> args = new ArrayList<>(arguments.length);
      for(int i = 0; i < arguments.length; i++) {
        args.add(map.get(arguments[i]));
      }
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
  
  public static DefaultConstructFunction of(Constructor c) {
    try {
      MapConstructor mc = c.getDeclaredAnnotation(MapConstructor.class);
      return new DefaultConstructFunction(MethodHandles.publicLookup().unreflectConstructor(c), (mc != null ? mc.value() : null));
    }
    catch(IllegalAccessException e) {
      throw new MappingException(e);
    }
  }

  @Override
  public String toString() {
    return "ConstructFunction{" + "handle=" + handle + ", arguments=" + Arrays.toString(arguments) + '}';
  }
  
}
