/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom.mapping;

import java.util.List;

/**
 *
 * @author F6036477
 */
public interface ExtractStrategy {
  
  public List<ExtractFunction> extractors(Class cls);
  
}