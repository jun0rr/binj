/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class ParamTypesConstructFunction implements ConstructFunction {
  
  private final MethodHandle handle;
  
  private final List<Class> types;
  
  public ParamTypesConstructFunction(MethodHandle mh, Class... types) {
    this.handle = Objects.requireNonNull(mh);
    this.types = (types == null ? Collections.EMPTY_LIST : List.of(types));
  }
  
  public ParamTypesConstructFunction(MethodHandle mh, List<Class> types) {
    this.handle = Objects.requireNonNull(mh);
    this.types = Objects.requireNonNull(types);
  }
  
  @Override
  public List arguments() {
    return types;
  }
  
  @Override
  public List parameters() {
    return types;
  }
  
  @Override
  public <T> T create(Map<String,Object> map) {
    try {
      if(types.stream().allMatch(t->map.values().stream()
          .anyMatch(o->t.isAssignableFrom(o.getClass())))) {
        return (T) handle.invokeWithArguments(types.stream()
            .map(t->map.values().stream().filter(o->t.isAssignableFrom(o.getClass())).findFirst().get())
            .toList());
      }
      else {
        throw new IllegalArgumentException("Parameter types does not match value types: " 
          + types.stream().filter(t->map.values().stream()
              .noneMatch(o->t.isAssignableFrom(o.getClass()))).toList());
      }
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  @Override
  public <T> T create() {
    try {
      return (T) handle.invoke();
    }
    catch(Throwable t) {
      throw new MappingException(t);
    }
  }
  
  @Override
  public String toString() {
    return "ConstructFunction{" + "handle=" + handle + ", arguments=" + types + '}';
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 19 * hash + Objects.hashCode(this.handle);
    hash = 19 * hash + Objects.hashCode(this.types);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ParamTypesConstructFunction other = (ParamTypesConstructFunction) obj;
    if (!Objects.equals(this.handle, other.handle)) {
      return false;
    }
    return Objects.equals(this.types, other.types);
  }
  
}
