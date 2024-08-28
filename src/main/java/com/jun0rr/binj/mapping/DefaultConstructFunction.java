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
public class DefaultConstructFunction implements ConstructFunction {
  
  private final MethodHandle handle;
  
  private final List arguments;
  
  public DefaultConstructFunction(MethodHandle mh, String... args) {
    this.handle = Objects.requireNonNull(mh);
    this.arguments = (args == null ? Collections.EMPTY_LIST : List.of(args));
  }
  
  public DefaultConstructFunction(MethodHandle mh, List args) {
    this.handle = Objects.requireNonNull(mh);
    this.arguments = Objects.requireNonNull(args);
  }
  
  
  @Override
  public List parameters() {
    return arguments;
  }
  
  @Override
  public <T> T create(Map<String,Object> map) {
    try {
      List args = arguments.stream()
          .map(a->map.get(a))
          .toList();
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
  
  @Override
  public String toString() {
    return "ConstructFunction{" + "handle=" + handle + ", arguments=" + arguments + '}';
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 19 * hash + Objects.hashCode(this.handle);
    hash = 19 * hash + Objects.hashCode(this.arguments);
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
    final DefaultConstructFunction other = (DefaultConstructFunction) obj;
    if (!Objects.equals(this.handle, other.handle)) {
      return false;
    }
    return Objects.equals(this.arguments, other.arguments);
  }
  
}
