/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 *
 * @author F6036477
 */
public class FieldsOrderConstructStrategy extends AbstractInvokeStrategy<ConstructFunction> {

  @Override
  public List<ConstructFunction> invokers(Class cls) {
    List<ConstructFunction> fns = cache.get(cls);
    if(fns == null) {
      List<Field> fields = List.of(cls.getDeclaredFields()).stream()
          .filter(f->!Modifier.isTransient(f.getModifiers()))
          .filter(f->!Modifier.isStatic(f.getModifiers()))
          .toList();
      fns = List.of(cls.getDeclaredConstructors()).stream()
          .filter(c->Modifier.isPublic(c.getModifiers()))
          .filter(c->c.getParameterCount() == fields.size())
          .filter(c->compare(fields, c.getParameterTypes()))
          .map(c->ConstructFunction.ofFields(c, fields.stream().map(Field::getName).toList()))
          //.peek(c->System.out.printf("* FieldsOrderConstructStrategy.constructor: %s%n", c))
          .toList();
      cache.put(cls, fns);
    }
    return fns;
  }
  
  private boolean compare(List<Field> fields, Class[] types) {
    boolean match = fields.size() == types.length;
    if(match) {
      for(int i = 0; i < fields.size(); i++) {
        match = match && fields.get(i).getType().equals(types[i]);
      }
    }
    return match;
  }
  
}
