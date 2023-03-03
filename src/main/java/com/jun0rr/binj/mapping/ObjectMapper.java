/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

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
  
  private final List<InvokeStrategy<ConstructFunction>> constructors;
  
  private final List<InvokeStrategy<ExtractFunction>> extractors;
  
  private final List<InvokeStrategy<InjectFunction>> injectors;
  
  public ObjectMapper() {
    this.constructors = new CopyOnWriteArrayList<>();
    this.extractors = new CopyOnWriteArrayList<>();
    this.injectors = new CopyOnWriteArrayList<>();
  }
  
  public List<InvokeStrategy<ConstructFunction>> constructStrategies() {
    return constructors;
  }
  
  public List<InvokeStrategy<ExtractFunction>> extractStrategies() {
    return extractors;
  }
  
  public List<InvokeStrategy<InjectFunction>> injectStrategies() {
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
        .flatMap(c->c.invokers(cls).stream())
        .collect(Collectors.toList());
    Optional<ConstructFunction> cct = cs.stream()
        .sorted((a,b)->Integer.compare(a.arguments().size(), b.arguments().size()) * -1)
        .filter(c->c.arguments().size() <= map.size())
        .filter(c->c.arguments().stream().allMatch(s->map.keySet().stream().anyMatch(k->s.equals(k))))
        .findFirst();
    if(cct.isEmpty()) {
      cct = cs.stream().filter(c->c.arguments().size() == 0).findAny();
    }
    ConstructFunction cf = cct.orElseThrow(()->new MappingException("No ConstructFunction found for " + cls.getCanonicalName()));
    return cf.create(map);
  }
  
  private <T> T inject(T obj, Map<String,Object> map) {
    injectors.stream().flatMap(i->i.invokers(obj.getClass()).stream())
        .filter(i->map.containsKey(i.name()))
        .forEach(i->i.inject(obj, map.get(i.name())));
    return obj;
  }
  
  private Map<String,Object> extract(Object obj, Map<String,Object> map) {
    if(extractors.isEmpty()) {
      throw new MappingException("No ExtractStrategy found");
    }
    extractors.stream().flatMap(e->e.invokers(obj.getClass()).stream())
        .forEach(e->map.put(e.name(), e.extract(obj)));
    //System.out.printf("ObjectMapper.extract( %s ): map=%s%n", obj, map);
    return map;
  }
  
  
  
  public static ObjectMapper withAnnotationStrategies() {
    ObjectMapper om = new ObjectMapper();
    om.constructStrategies().add(new AnnotationConstructStrategy());
    om.extractStrategies().add(new AnnotationExtractStrategy());
    om.injectStrategies().add(new AnnotationInjectStrategy());
    return om;
  }
  
  public static ObjectMapper withGetterSetterStrategies() {
    ObjectMapper om = new ObjectMapper();
    om.constructStrategies().add(new NoArgsConstructStrategy());
    om.extractStrategies().add(new GetterStrategy());
    om.injectStrategies().add(new SetterStrategy());
    return om;
  }
  
  public static ObjectMapper withFieldStrategies() {
    ObjectMapper om = new ObjectMapper();
    om.constructStrategies().add(new NoArgsConstructStrategy());
    om.extractStrategies().add(new FieldExtractStrategy());
    om.injectStrategies().add(new FieldInjectStrategy());
    return om;
  }
  
}
