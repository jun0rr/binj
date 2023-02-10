/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import com.jun0rr.jbom.buffer.BinBuffer;
import com.jun0rr.jbom.impl.DefaultBinContext;
import com.jun0rr.jbom.mapping.ObjectMapper;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author F6036477
 */
public interface BinContext {
  
  public <T> BinCodec<T> getBinCodec(BinType<T> type) throws BinTypeNotFoundException;
  
  public <T> BinCodec<T> getBinCodec(Class<T> cls);
  
  public <T> BinCodec<T> getBinCodec(long id) throws UnknownBinTypeException;
  
  public <T> BinType<T> getBinType(Class<T> cls);
  
  public <T> BinType<T> getBinType(long id) throws UnknownBinTypeException;
  
  public BinType putIfAbsent(BinType t, BinCodec c);
  
  public BinType putIfAbsent(Class c, BinCodec d);
  
  public Map<BinType,BinCodec> codecs();
  
  public ObjectMapper mapper();
  
  public <T> T read(BinBuffer buf) throws UnknownBinTypeException;
  
  public <T> ContextEvent write(BinBuffer buf, T o) throws BinTypeNotFoundException;
  
  public default <T> T read(ByteBuffer buf) {
    return read(BinBuffer.of(buf));
  }
  
  public default <T> void write(ByteBuffer buf, T val) {
    write(BinBuffer.of(buf), val);
  }
  
  public int calcSize(Object o) throws BinTypeNotFoundException;
  
  public List<ContextListener> listeners();
  
  
  public static BinContext of(ObjectMapper om) {
    return new DefaultBinContext(om);
  }
  
  public static BinContext newContext() {
    return new DefaultBinContext(new ObjectMapper());
  }
  
}
