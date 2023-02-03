/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

/**
 *
 * @author F6036477
 */
public interface ContextListener {
  
  public void write(ContextEvent e);
  
  public void read(ContextEvent e);
  
  
  
  public static interface ContextEvent {
  
    public int size();

    public long checksum();

    public BinType type();

  }
  
}
