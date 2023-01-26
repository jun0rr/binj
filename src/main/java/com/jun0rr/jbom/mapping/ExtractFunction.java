/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class ExtractFunction {
  
  private final String name;
  
  private final MethodHandle handle;
  
  public ExtractFunction(String name, MethodHandle mh) {
    this.name = Objects.requireNonNull(name);
    this.handle = Objects.requireNonNull(mh);
  }
  
  public String name() {
    return name;
  }

  public Object extract(Object o) {
    try {
      return handle.invoke(o);
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  public static ExtractFunction of(Method m) {
    try {
      MethodHandle mh = MethodHandles.publicLookup().findVirtual(
          m.getDeclaringClass(), m.getName(), 
          MethodType.methodType(m.getReturnType())
      );
      return new ExtractFunction(m.getName(), mh);
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
  public static ExtractFunction of(Field f) {
    try {
      MethodHandle mh = MethodHandles.publicLookup().findGetter(
          f.getDeclaringClass(), f.getName(), f.getType()
      );
      return new ExtractFunction(f.getName(), mh);
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
}
