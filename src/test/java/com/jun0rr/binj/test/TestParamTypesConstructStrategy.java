/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.codec.ObjectCodec;
import com.jun0rr.binj.mapping.ParamTypesConstructStrategy;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestParamTypesConstructStrategy {
  
  @Test
  public void test() {
    ParamTypesConstructStrategy pcs = new ParamTypesConstructStrategy();
    pcs.invokers(ObjectCodec.class)
        .forEach(System.out::println);
  }
  
}
