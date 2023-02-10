/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinType;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public abstract class AbstractBinCodec<T> implements BinCodec<T> {
  
  protected final BinType<T> type;
  
  public AbstractBinCodec(BinType<T> type) {
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public BinType<T> bintype() {
    return type;
  }

  @Override
  public String toString() {
    return String.format("%s{%s}", getClass().getSimpleName(), type);
  }

}
