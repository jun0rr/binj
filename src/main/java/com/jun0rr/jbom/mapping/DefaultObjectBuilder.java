/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author F6036477
 */
public class DefaultObjectBuilder implements ObjectBuilder {
  
  private final InjectStrategy inject;
  
  public DefaultObjectBuilder(InjectStrategy is) {
    this.inject = Objects.requireNonNull(is);
  }

  @Override
  public <T> T build(Class<T> cls, Map<String, Object> map) {
    Constructor cct = List.of(cls.getDeclaredConstructors()).stream()
        .filter(c->c.getParameterCount() == 0)
        .findAny()
        .orElseThrow(()->new MappingException("No public no-args constructor found for " + cls));
    try {
      MethodHandle handle = MethodHandles.publicLookup().findConstructor(cls, MethodType.)
        
    
    List<InjectFunction> ls = inject.injectors(cls);
    ls.stream()
        .filter(i->map.containsKey(i.name()))
        .forEach(i->);
  }
  
}
