/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.buffer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 *
 * @author Juno
 */
public class FileNameSupplier implements Supplier<String> {
  
  private final String base;
  
  private final Supplier<String> suffix;
  
  private final String extension;
  
  private final AtomicInteger extSuffix;
  
  public FileNameSupplier(String base, Supplier<String> suffix, String ext, int extSuffix) {
    this.base = Objects.requireNonNull(base);
    this.suffix = suffix;
    this.extension = Objects.requireNonNull(ext);
    this.extSuffix = new AtomicInteger(extSuffix);
  }
  
  public FileNameSupplier(String base, String ext, int extSuffix) {
    this(base, null, ext, extSuffix);
  }
  
  public FileNameSupplier(String base, String ext) {
    this(base, null, ext, 0);
  }
  
  @Override
  public String get() {
    StringBuilder sb = new StringBuilder();
    sb.append(base);
    if(suffix != null) {
      sb.append("_").append(suffix.get());
    }
    String name = sb.append(".")
        .append(extension)
        .append(extSuffix.getAndIncrement())
        .toString();
    //System.out.println("* FileNameSupplier.get(): " + name);
    return name;
  }
  
}
