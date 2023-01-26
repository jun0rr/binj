/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.Map;

/**
 *
 * @author F6036477
 */
public interface ObjectBuilder {
  
  public <T> T build(Class<T> cls, Map<String,Object> map);
  
}
