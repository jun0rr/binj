/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.List;
import java.util.Map;

/**
 *
 * @author F6036477
 */
public interface ConstructFunction {
  
  public List<String> arguments();
  
  public <T> T create(Map<String,Object> map);
  
  public <T> T create();
  
}
