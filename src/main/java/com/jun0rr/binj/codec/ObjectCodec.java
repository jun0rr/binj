/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.codec;

import com.jun0rr.binj.BinContext;
import com.jun0rr.binj.BinType;
import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.mapping.ObjectMapper;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class ObjectCodec<T> extends AbstractBinCodec<T> {
  
  private final ObjectMapper mapper;
  
  private final BinContext ctx;
  
  public ObjectCodec(BinContext ctx, ObjectMapper mapper, BinType<T> type) {
    super(type);
    this.mapper = Objects.requireNonNull(mapper);
    this.ctx = Objects.requireNonNull(ctx);
  }

  @Override
  public T read(BinBuffer buf) {
    long id = buf.getLong();
    BinType type = ctx.getBinType(id);
    Map<String,Object> map = ctx.read(buf);
    map.put(ObjectMapper.KEY_CLASS, type.type());
    return (T) mapper.unmap(map);
  }

  @Override
  public void write(BinBuffer buf, T val) {
    Map<String,Object> map = mapper.map(val);
    Class<T> cls = (Class) map.remove(ObjectMapper.KEY_CLASS);
    BinType type = ctx.putIfAbsent(cls, this);
    buf.putLong(type.id());
    ctx.write(buf, map);
  }

  @Override
  public int calcSize(T val) {
    Map<String,Object> map = mapper.map(val);
    map.remove(ObjectMapper.KEY_CLASS);
    return Long.BYTES + ctx.calcSize(map);
  }
  
}
