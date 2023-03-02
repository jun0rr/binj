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
public interface ExtractFunction {
  
  public String name();
  
  public Object extract(Object obj);
  
  
  public static ExtractFunction of(Method m) {
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
  
  public static ExtractFunction of(Field f) {
    try {
      return new DefaultExtractFunction(f.getName(), MethodHandles.publicLookup().unreflectGetter(f));
    }
    catch(Exception e) {
      throw new MappingException(e);
    }
  }
  
}
