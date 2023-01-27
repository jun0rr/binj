/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class ObjectMapper {
  
  public static final String KEY_CLASS = "$class";
  
  private final List<ConstructStrategy> constructors;
  
  private final List<ExtractStrategy> extractors;
  
  private final List<InjectStrategy> injectors;
  
  public ObjectMapper() {
    this.constructors = new CopyOnWriteArrayList<>();
    this.extractors = new CopyOnWriteArrayList<>();
    this.injectors = new CopyOnWriteArrayList<>();
  }
  
  public List<ConstructStrategy> constructStrategy() {
    return constructors;
  }
  
  public List<ExtractStrategy> extractStrategy() {
    return extractors;
  }
  
  public List<InjectStrategy> injectStrategy() {
    return injectors;
  }
  
  public Map<String,Object> map(Object o) {
    Map<String,Object> map = new TreeMap<>();
    map.put(KEY_CLASS, o.getClass());
    return extract(o, map);
  }
  
  public <T> T unmap(Map<String,Object> map) {
    return inject(create(map), map);
  }
  
  private <T> T create(Map<String,Object> map) {
    if(constructors.isEmpty()) {
      throw new MappingException("No ConstructStrategy found");
    }
    if(!map.containsKey(KEY_CLASS)) {
      throw new MappingException("$class key not found");
    }
    Class<T> cls = (Class<T>) map.get(KEY_CLASS);
    List<ConstructFunction> cs = constructors.stream()
        .flatMap(c->c.constructors(cls).stream())
        .collect(Collectors.toList());
    Optional<ConstructFunction> cct = cs.stream()
        .sorted((a,b)->Integer.compare(a.arguments().length, b.arguments().length) * -1)
        .filter(c->c.arguments().length <= map.size())
        .filter(c->List.of(c.arguments()).stream().allMatch(s->map.keySet().stream().anyMatch(k->s.equals(k))))
        .findFirst();
    if(cct.isEmpty()) {
      cct = cs.stream().filter(c->c.arguments().length == 0).findAny();
    }
    ConstructFunction cf = cct.orElseThrow(()->new MappingException("No ConstructFunction found"));
    return cf.create(map);
  }
  
  private <T> T inject(T obj, Map<String,Object> map) {
    injectors.stream().flatMap(i->i.injectors(obj.getClass()).stream())
        .filter(i->map.containsKey(i.name()))
        .forEach(i->i.inject(obj, map.get(i.name())));
    return obj;
  }
  
  private Map<String,Object> extract(Object obj, Map<String,Object> map) {
    if(extractors.isEmpty()) {
      throw new MappingException("No ExtractStrategy found");
    }
    extractors.stream().flatMap(e->e.extractors(obj.getClass()).stream())
        .forEach(e->map.put(e.name(), e.extract(obj)));
    return map;
  }
  
}
