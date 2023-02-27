/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.binj.buffer;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Juno
 */
public interface PathSupplier extends Supplier<Path> {
  
  public Stream<Path> existing();
  
  
  public static PathSupplier of(Path root, String base, Supplier<String> suffix, String ext, int extSuffix) {
    return new DefaultPathSupplier(root, base, suffix, ext, extSuffix);
  }
  
  public static PathSupplier of(Path root, String base, String ext, int extSuffix) {
    return new DefaultPathSupplier(root, base, null, ext, extSuffix);
  }
  
  public static PathSupplier of(Path root, String base, String ext) {
    return new DefaultPathSupplier(root, base, null, ext, 0);
  }
  
}
