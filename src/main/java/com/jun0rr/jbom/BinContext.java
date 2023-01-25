/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import com.jun0rr.jbom.buffer.BinBuffer;
import java.nio.ByteBuffer;
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
  
  public Map<BinType,BinCodec> codecs();
  
  public <T> T read(BinBuffer buf) throws UnknownBinTypeException;
  
  public <T> void write(BinBuffer buf, T o) throws BinTypeNotFoundException;
  
  public default <T> T read(ByteBuffer buf) {
    return read(BinBuffer.of(buf));
  }
  
  public default <T> void write(ByteBuffer buf, T val) {
    write(BinBuffer.of(buf), val);
  }
  
  public int calcSize(Object o) throws BinTypeNotFoundException;
  
}
