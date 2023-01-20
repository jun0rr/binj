/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public interface BinBuffer {
  
  public int position();
  
  public int limit();
  
  public int capacity();
  
  public BinBuffer compact();
  
  public BinBuffer duplicate();
  
  public BinBuffer slice();
  
  public byte get();
  
  public BinBuffer get(byte[] array);
  
  public BinBuffer get(byte[] array, int offset, int length);
  
  public char getChar();
  
  public double getDouble();
  
  public float getFloat();
  
  public int getInt();
  
  public long getLong();
  
  public short getShort();
  
  public BinBuffer put(byte b);
  
  public BinBuffer put(byte[] array);
  
  public BinBuffer put(byte[] array, int offset, int length);
  
  public BinBuffer put(ByteBuffer buf);
  
  public BinBuffer put(BinBuffer buf);
  
}
