/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author F6036477
 */
public interface InjectFunction {
  
  public String name();
  
  public void inject(Object obj, Object val);
  
  
  public static InjectFunction of(Method m) {
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
  
  public static InjectFunction of(Field f) {
    try {
      return new DefaultInjectFunction(f.getName(), MethodHandles.publicLookup().unreflectSetter(f));
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
}
