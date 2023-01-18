/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.codec;

import com.jun0rr.jbon.BinCodec;
import com.jun0rr.jbon.BinContext;
import com.jun0rr.jbon.BinType;
import com.jun0rr.jbon.impl.DefaultBinType;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class MapCodec implements BinCodec<Map> {
  
  private final BinContext ctx;
  
  public MapCodec(BinContext ctx) {
    this.ctx = Objects.requireNonNull(ctx);
  }

  @Override
  public Map read(ByteBuffer buf) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void write(ByteBuffer buf, Map val) {
    buf.putLong(bintype().id());
    
  }

  @Override
  public int calcSize(Map val) {
    //[ID:LONG,LENGTH:INT,KEYS:INT,]
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public BinType<Map> bintype() {
    return DefaultBinType.MAP;
  }
  
}
