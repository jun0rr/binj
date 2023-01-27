/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinType;
import com.jun0rr.jbom.mapping.ObjectMapper;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class ObjectCodec<T> implements BinCodec<T> {
  
  private final ObjectMapper mapper;
  
  private final BinContext ctx;
  
  private final BinType<T> type;
  
  public ObjectCodec(BinContext ctx, ObjectMapper mapper, BinType<T> type) {
    this.mapper = Objects.requireNonNull(mapper);
    this.ctx = Objects.requireNonNull(ctx);
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public BinType bintype() {
    return type;
  }

  @Override
  public T read(BinBuffer buf) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void write(BinBuffer buf, T val) {
    Map<String,Object> map = mapper.map(val);
    Class<T> cls = (Class) map.get(ObjectMapper.KEY_CLASS);
    ctx.codecs().put(new DefaultBinType(cls), this);
    
  }

  @Override
  public int calcSize(T val) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
  
}
