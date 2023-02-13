/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj;

/**
 *
 * @author F6036477
 */
public interface ContextEvent {
  
  public int size();

  public long checksum();

  public BinCodec codec();
  
}
