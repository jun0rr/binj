/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class DefaultExtractFunction implements ExtractFunction {
  
  private final String name;
  
  private final MethodHandle handle;
  
  public DefaultExtractFunction(String name, MethodHandle mh) {
    this.name = Objects.requireNonNull(name);
    this.handle = Objects.requireNonNull(mh);
  }
  
  @Override
  public String name() {
    return name;
  }

  @Override
  public Object extract(Object o) {
    try {
      return handle.invoke(o);
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  public static DefaultExtractFunction of(Method m) {
    try {
      return new DefaultExtractFunction(
          MethodNameAdapter.adapt(m), 
          MethodHandles.publicLookup().unreflect(m)
      );
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
  public static DefaultExtractFunction of(Field f) {
    try {
      return new DefaultExtractFunction(f.getName(), MethodHandles.publicLookup().unreflectGetter(f));
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
}
