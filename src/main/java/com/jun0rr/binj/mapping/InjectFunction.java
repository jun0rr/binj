/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.mapping;

/**
 *
 * @author F6036477
 */
public interface InjectFunction {
  
  public String name();
  
  public void inject(Object obj, Object val);
  
}