/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbon.codec;

import com.jun0rr.jbon.BinCodec;
import com.jun0rr.jbon.BinContext;
import com.jun0rr.jbon.BinType;
import com.jun0rr.jbon.IndexedKey;
import com.jun0rr.jbon.impl.DefaultBinType;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class IndexedKeyCodec implements BinCodec<IndexedKey> {
  
  private final BinContext ctx;
  
  public IndexedKeyCodec(BinContext ctx) {
    this.ctx = Objects.requireNonNull(ctx);
  }

  @Override
  public IndexedKey read(ByteBuffer buf) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void write(ByteBuffer buf, IndexedKey val) {
    
  }

  @Override
  public int calcSize(IndexedKey val) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public BinType<IndexedKey> bintype() {
    return DefaultBinType.IDXKEY;
  }
  
}
