/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon;

import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public interface BinCodec<V> extends Typeable<V> {
  
  public V read(ByteBuffer buf);
  
  public void write(ByteBuffer buf, V val);
  
  public int calcSize(V val);
  
}
