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

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.name);
    hash = 59 * hash + Objects.hashCode(this.handle);
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
    final DefaultInjectFunction other = (DefaultInjectFunction) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return Objects.equals(this.handle, other.handle);
  }

  @Override
  public String toString() {
    return "InjectFunction{" + "name=" + name + ", handle=" + handle + '}';
  }
  
}
