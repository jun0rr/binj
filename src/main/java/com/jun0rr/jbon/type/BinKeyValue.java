/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbon.type;

import com.jun0rr.jbon.BinType;

/**
 *
 * @author F6036477
 */
public interface BinKeyValue<V> {
  
  public String key();
  
  public V value();
  
  public BinType<V> vtype();
  
}
