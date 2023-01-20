/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

/**
 *
 * @author F6036477
 */
public interface BinBufferAllocator {
  
  public int bufferSize();
  
  public BinBuffer alloc();
  
  public BinBuffer alloc(int size);
  
}
