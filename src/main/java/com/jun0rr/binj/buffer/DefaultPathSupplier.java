/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.buffer;

import com.jun0rr.unchecked.Unchecked;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 *
 * @author Juno
 */
public class DefaultPathSupplier implements PathSupplier {
  
  private final Path root;
  
  private final String base;
  
  private final Supplier<String> suffix;
  
  private final String extension;
  
  private final AtomicInteger extSuffix;
  
  public DefaultPathSupplier(Path root, String base, Supplier<String> suffix, String ext, int extSuffix) {
    this.root = Objects.requireNonNull(root);
    this.base = Objects.requireNonNull(base);
    this.suffix = suffix;
    this.extension = Objects.requireNonNull(ext);
    this.extSuffix = new AtomicInteger(extSuffix);
  }
  
  @Override
  public Path get() {
    StringBuilder sb = new StringBuilder();
    sb.append(base);
    if(suffix != null) {
      sb.append("_").append(suffix.get());
    }
    String name = sb.append(".")
        .append(extension)
        .append(extSuffix.getAndIncrement())
        .toString();
    return root.resolve(name);
  }
  
  @Override
  public Stream<Path> existing() {
    String name = String.format("^%s.*\\.%s", base, extension);
    Predicate<String> ftest = Pattern.compile(String.format(name.concat("\\d+$"), base, extension)).asMatchPredicate();
    ToIntFunction<Path> fti = p->Integer.parseInt(p.getFileName().toString().replaceAll(name, ""));
    return Unchecked.call(()->Files.list(root)
        .filter(p->ftest.test(p.getFileName().toString()))
        .sorted((p,q)->Integer.compare(fti.applyAsInt(p), fti.applyAsInt(q)))
    );
  }
  
}
