/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
public class InjectFunction {
  
  private final String name;
  
  private final MethodHandle handle;
  
  public InjectFunction(String name, MethodHandle mh) {
    this.name = Objects.requireNonNull(name);
    this.handle = Objects.requireNonNull(mh);
  }
  
  public String name() {
    return name;
  }
  
  public void inject(Object obj, Object val) {
    try {
      handle.invoke(obj, val);
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  public static InjectFunction of(Method m) {
    try {
      MethodHandle mh = MethodHandles.publicLookup().findVirtual(
          m.getDeclaringClass(), m.getName(), 
          MethodType.methodType(m.getReturnType(), m.getParameterTypes())
      );
      return new InjectFunction(m.getName(), mh);
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
  public static InjectFunction of(Field f) {
    try {
      MethodHandle mh = MethodHandles.publicLookup().findSetter(
          f.getDeclaringClass(), f.getName(), f.getType()
      );
      return new InjectFunction(f.getName(), mh);
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
}
