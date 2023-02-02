/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

/**
 *
 * @author F6036477
 */
public interface WriteEvent {
  
  public int size();
  
  public byte[] hash();
  
  public String hashString();
  
  public BinType type();
  
}
