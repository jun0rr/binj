/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom.map;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @author F6036477
 */
public interface MappingStrategy {
  
  public List<Function> supply(Object o);
  
  public List<Function> consume(Object o);
  
}
