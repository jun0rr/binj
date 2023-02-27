/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import com.jun0rr.binj.buffer.PathSupplier;
import com.jun0rr.binj.impl.Rethrow;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Juno
 */
public class TestFileNameSupplier {
  
  @Test public void test() {
    Path root = Paths.get("./");
    PathSupplier sup = PathSupplier.of(root, getClass().getSimpleName(), "ext");
    IntStream.range(0, 20).mapToObj(i->sup.get())
        .peek(p->System.out.println(p))
        .peek(p->Rethrow.run(()->Files.createFile(p)))
        .collect(Collectors.toList());
    System.out.println("--------------------");
    sup.existing().forEach(System.out::println);
    sup.existing().forEach(p->Rethrow.run(()->Files.delete(p)));
  }
  
}
