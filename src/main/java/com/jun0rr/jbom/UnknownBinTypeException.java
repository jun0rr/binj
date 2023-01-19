/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom;

/**
 *
 * @author F6036477
 */
public class UnknownBinTypeException extends RuntimeException {
  
  public UnknownBinTypeException(long id) {
    super(String.valueOf(id));
  }
  
}
