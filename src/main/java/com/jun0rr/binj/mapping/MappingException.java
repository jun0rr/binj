/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

/**
 *
 * @author F6036477
 */
public class MappingException extends RuntimeException {
  
  public MappingException(String msg) {
    super(msg);
  }
  
  public MappingException(String msg, Object... args) {
    super(String.format(msg, args));
  }
  
  public MappingException(Throwable cause) {
    super(cause);
  }
  
  public MappingException(Throwable cause, String msg) {
    super(msg, cause);
  }
  
  public MappingException(Throwable cause, String msg, Object... args) {
    super(String.format(msg, args), cause);
  }
  
}
