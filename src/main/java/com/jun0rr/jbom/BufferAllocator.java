/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import java.io.Closeable;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public interface BufferAllocator extends Closeable {
  
  public int bufferSize();
  
  public ByteBuffer alloc();
  
  public ByteBuffer alloc(int size);
  
}
