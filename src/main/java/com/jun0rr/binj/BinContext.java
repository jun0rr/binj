/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj;

import com.jun0rr.binj.buffer.BinBuffer;
import com.jun0rr.binj.impl.DefaultBinContext;
import com.jun0rr.binj.mapping.AnnotationConstructStrategy;
import com.jun0rr.binj.mapping.AnnotationGetStrategy;
import com.jun0rr.binj.mapping.AnnotationSetStrategy;
import com.jun0rr.binj.mapping.DefaultConstructStrategy;
import com.jun0rr.binj.mapping.FieldGetStrategy;
import com.jun0rr.binj.mapping.FieldMethodGetStrategy;
import com.jun0rr.binj.mapping.FieldMethodSetStrategy;
import com.jun0rr.binj.mapping.FieldSetStrategy;
import com.jun0rr.binj.mapping.FieldsOrderConstructStrategy;
import com.jun0rr.binj.mapping.GetterMethodStrategy;
import com.jun0rr.binj.mapping.NoArgsConstructStrategy;
import com.jun0rr.binj.mapping.ObjectMapper;
import com.jun0rr.binj.mapping.ParamTypesConstructStrategy;
import com.jun0rr.binj.mapping.SetterMethodStrategy;
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
  
  public static BinContext ofCombinedStrategyMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.constructStrategies()
        .put(1, new FieldsOrderConstructStrategy())
        .put(2, new AnnotationConstructStrategy())
        .put(3, new DefaultConstructStrategy())
        .put(4, new NoArgsConstructStrategy())
        .put(5, new ParamTypesConstructStrategy());
    mapper.extractStrategies()
        .put(1, new GetterMethodStrategy())
        .put(2, new FieldMethodGetStrategy())
        .put(3, new AnnotationGetStrategy())
        .put(4, new FieldGetStrategy());
    mapper.injectStrategies()
        .put(1, new SetterMethodStrategy())
        .put(2, new FieldMethodSetStrategy())
        .put(3, new AnnotationSetStrategy())
        .put(4, new FieldSetStrategy());
    return new DefaultBinContext(mapper);
  }
  
}
