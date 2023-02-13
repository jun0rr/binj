/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Method;

/**
 *
 * @author F6036477
 */
public class MethodNameAdapter {
  
  public static String adapt(Method m) {
    String name = m.getName();
    if(name.startsWith("get") || name.startsWith("set")) {
      name = name.substring(3);
      name = name.substring(0, 1).toLowerCase().concat(name.substring(1, name.length()));
    }
    return name;
  }
  
}
