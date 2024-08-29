/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author F6036477
 */
public class ObjectMapper {
  
  public static final String KEY_CLASS = "$class";
  
  private final CombinedStrategy<ConstructFunction> constructors;
  
  private final CombinedStrategy<ExtractFunction> extractors;
  
  private final CombinedStrategy<InjectFunction> injectors;
  
  public ObjectMapper() {
    this.constructors = new CombinedStrategy();
    this.extractors = new CombinedStrategy();
    this.injectors = new CombinedStrategy();
  }
  
  public CombinedStrategy<ConstructFunction> constructStrategies() {
    return constructors;
  }
  
  public CombinedStrategy<ExtractFunction> extractStrategies() {
    return extractors;
  }
  
  public CombinedStrategy<InjectFunction> injectStrategies() {
    return injectors;
  }
  
  public Map<String,Object> map(Object o) {
    Map<String,Object> map = new LinkedHashMap<>();
    map.put(KEY_CLASS, o.getClass());
    return extract(o, map);
  }
  
  public <T> T unmap(Map<String,Object> map) {
    return inject(create(map), map);
  }
  
  private <T> T create(Map<String,Object> map) {
    if(constructors.strategies().isEmpty()) {
      throw new MappingException("No ConstructStrategy found");
    }
    if(!map.containsKey(KEY_CLASS)) {
      throw new MappingException("$class key not found");
    }
    Class<T> cls = (Class<T>) map.get(KEY_CLASS);
    List<ConstructFunction> cs = constructors.invokers(cls);
    Optional<ConstructFunction> cct = cs.stream()
        .sorted((a,b)->Integer.compare(a.parameters().size(), b.parameters().size()) * -1)
        //.peek(c->System.out.printf("* ObjectMapper.constructor: %s%n", c))
        .filter(c->c.parameters().size() <= map.size())
        .filter(c->c.parameters().stream().allMatch(s->map.keySet().stream().anyMatch(k->s.equals(k))))
        .findFirst();
    if(cct.isEmpty()) {
      cct = cs.stream().filter(c->c.parameters().size() == 0).findAny();
    }
    //System.out.printf("* ObjectMapper.selected: %s%n", cct);
    ConstructFunction cf = cct.orElseThrow(()->new MappingException("No ConstructFunction found for " + cls.getCanonicalName()));
    return cf.create(map);
  }
  
  private <T> T inject(T obj, Map<String,Object> map) {
    if(!injectors.strategies().isEmpty()) {
      injectors.invokers(obj.getClass()).stream()
          .filter(i->map.containsKey(i.name()))
          .forEach(i->i.inject(obj, map.get(i.name())));
    }
    return obj;
  }
  
  private Map<String,Object> extract(Object obj, Map<String,Object> map) {
    if(extractors.strategies().isEmpty()) {
      throw new MappingException("No ExtractStrategy found");
    }
    extractors.invokers(obj.getClass()).stream()
        .forEach(e->map.put(e.name(), e.extract(obj)));
    return map;
  }
  
  
  
  public static ObjectMapper withAnnotationStrategies() {
    ObjectMapper om = new ObjectMapper();
    om.constructStrategies().put(1, new AnnotationConstructStrategy());
    om.extractStrategies().put(1, new AnnotationGetStrategy());
    om.injectStrategies().put(1, new AnnotationSetStrategy());
    return om;
  }
  
  public static ObjectMapper withGetterSetterStrategies() {
    ObjectMapper om = new ObjectMapper();
    om.constructStrategies().put(1, new NoArgsConstructStrategy());
    om.extractStrategies().put(1, new GetterMethodStrategy());
    om.injectStrategies().put(1, new SetterMethodStrategy());
    return om;
  }
  
  public static ObjectMapper withFieldStrategies() {
    ObjectMapper om = new ObjectMapper();
    om.constructStrategies().put(1, new NoArgsConstructStrategy());
    om.extractStrategies().put(1, new FieldGetStrategy());
    om.injectStrategies().put(1, new FieldSetStrategy());
    return om;
  }
  
}
