/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author F6036477
 */
public abstract class AbstractInvokeStrategy<T> implements InvokeStrategy<T> {
  
  protected final Map<Class,List<T>> cache;
  
  public AbstractInvokeStrategy() {
    cache = new ConcurrentHashMap<>();
  }

}
