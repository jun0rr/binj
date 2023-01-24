/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class ObjectCodec<T> implements BinCodec<T> {
  
  private final BinContext ctx;
  
  private final BinType<T> type;
  
  public ObjectCodec(BinContext ctx, BinType<T> type) {
    this.ctx = Objects.requireNonNull(ctx);
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public BinType bintype() {
    return type;
  }

  @Override
  public T read(ByteBuffer buf) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void write(ByteBuffer buf, T val) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public int calcSize(T val) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
  
}
