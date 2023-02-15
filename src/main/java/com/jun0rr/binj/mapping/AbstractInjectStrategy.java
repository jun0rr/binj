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
public abstract class AbstractInjectStrategy implements InjectStrategy {
  
  protected final Map<Class,List<InjectFunction>> cache;
  
  public AbstractInjectStrategy() {
    cache = new ConcurrentHashMap<>();
  }

}