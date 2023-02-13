/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
public class DefaultInjectFunction implements InjectFunction {
  
  private final String name;
  
  private final MethodHandle handle;
  
  public DefaultInjectFunction(String name, MethodHandle mh) {
    this.name = Objects.requireNonNull(name);
    this.handle = Objects.requireNonNull(mh);
  }
  
  public String name() {
    return name;
  }
  
  @Override
  public void inject(Object obj, Object val) {
    try {
      handle.invoke(obj, val);
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  public static DefaultInjectFunction of(Method m) {
    try {
      return new DefaultInjectFunction(
          MethodNameAdapter.adapt(m), 
          MethodHandles.publicLookup().unreflect(m)
      );
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
  public static DefaultInjectFunction of(Field f) {
    try {
      return new DefaultInjectFunction(f.getName(), MethodHandles.publicLookup().unreflectSetter(f));
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
}
