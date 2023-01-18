/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbon;

/**
 *
 * @author F6036477
 */
public interface BinValue<V> extends Typeable<V> {
  
  public BinCodec<V> codec();
  
  public V value();
  
}
