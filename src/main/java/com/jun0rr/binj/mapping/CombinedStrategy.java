/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author F6036477
 */
public class CombinedStrategy<T> extends AbstractInvokeStrategy<T> {
  
  private final Map<Integer,InvokeStrategy<T>> strategies;
  
  public CombinedStrategy() {
    this.strategies = new ConcurrentHashMap<>();
  }
  
  public static <U> CombinedStrategy<U> newStrategy() {
    return new CombinedStrategy<>();
  }
  
  public CombinedStrategy<T> put(int weight, InvokeStrategy<T> strategy) {
    this.strategies.put(weight, Objects.requireNonNull(strategy));
    return this;
  }
  
  public Map<Integer,InvokeStrategy<T>> strategies() {
    return strategies;
  }

  @Override
  public List<T> invokers(Class cls) {
    List<T> fns = cache.get(cls);
    if(fns == null) {
      if(strategies.isEmpty()) {
        throw new IllegalStateException("No strategies found");
      }
      fns = strategies.entrySet().stream()
          .sorted((a,b)->Integer.compare(a.getKey(), b.getKey()))
          .map(Entry::getValue)
          //.peek(s->System.out.printf("* Strategy: %s%n", s))
          .flatMap(s->s.invokers(cls).stream())
          .toList();
      cache.put(cls, fns);
    }
    return fns;
  }
  
}
