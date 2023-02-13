/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj;

import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class BinTypeNotFoundException extends RuntimeException {
  
  public BinTypeNotFoundException(Class type) {
    super(Objects.requireNonNull(type).getCanonicalName());
  }
  
  public BinTypeNotFoundException(int tag) {
    super(String.format("0x%s", Integer.toHexString(tag)));
  }
  
}
