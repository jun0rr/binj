/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.buffer;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 *
 * @author F6036477
 */
public class OverflowBufferAllocator implements BufferAllocator {

  @Override
  public int bufferSize() {
    return 0;
  }

  @Override
  public ByteBuffer alloc() {
    throw new BufferOverflowException();
  }

}
