/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbon;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 *
 * @author F6036477
 */
public interface BinContext {
  
  public <T> BinCodec<T> getBinCodec(BinType<T> cls) throws UnexpectedBinTypeException;
  
  public <T> BinCodec<T> getBinCodec(Class<T> cls) throws BinTypeNotFoundException;
  
  public <T> BinCodec<T> getBinCodec(long id) throws UnexpectedBinTypeException;
  
  public <T> BinType<T> getBinType(Class<T> cls) throws BinTypeNotFoundException;
  
  public <T> BinType<T> getBinType(long id) throws UnexpectedBinTypeException;
  
  public Map<BinType,BinCodec> codecs();
  
  public <T> T read(ByteBuffer buf) throws UnexpectedBinTypeException;
  
  public <T> void write(ByteBuffer buf, T o) throws BinTypeNotFoundException;
  
}
