/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom;

import com.jun0rr.jbom.buffer.BinBuffer;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public interface BinCodec<V> {
  
  public BinType<V> bintype();
  
  public V read(BinBuffer buf);
  
  public void write(BinBuffer buf, V val);
  
  public default V read(ByteBuffer buf) {
    return read(BinBuffer.of(buf));
  }
  
  public default void write(ByteBuffer buf, V val) {
    write(BinBuffer.of(buf), val);
  }
  
  public int calcSize(V val);
  
}
