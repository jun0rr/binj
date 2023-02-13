/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.buffer;

/**
 *
 * @author Juno
 */
public class MappedBinBuffer extends DefaultBinBuffer {
  
  public MappedBinBuffer(BufferAllocator alloc) {
    super(alloc);
  }
  
  @Override
  public void close() {
    malloc.close();
  }
  
}
