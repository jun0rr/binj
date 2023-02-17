/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.test;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestMappedBuffer {
  
  @Test
  public void test() throws IOException {
    FileChannel ch = FileChannel.open(Paths.get("./", getClass().getSimpleName().concat(".bin")), 
        StandardOpenOption.READ, 
        StandardOpenOption.WRITE, 
        StandardOpenOption.CREATE, 
        StandardOpenOption.TRUNCATE_EXISTING, 
        StandardOpenOption.SYNC
    );
    MappedByteBuffer buf = ch.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
    System.out.printf("* buf.isLoaded: %s%n", buf.isLoaded());
    System.out.printf("* buf.position: %d%n", buf.position());
    System.out.printf("* buf.limit...: %d%n", buf.limit());
    System.out.printf("* buf.capacity: %d%n", buf.capacity());
    System.out.printf("* ch.size.....: %d%n", ch.size());
    System.out.printf("* buf = ch.map(FileChannel.MapMode.READ_WRITE, ch.size(), 1024)%n");
    buf = ch.map(FileChannel.MapMode.READ_WRITE, ch.size(), 1024);
    System.out.printf("* buf.isLoaded: %s%n", buf.isLoaded());
    System.out.printf("* buf.position: %d%n", buf.position());
    System.out.printf("* buf.limit...: %d%n", buf.limit());
    System.out.printf("* buf.capacity: %d%n", buf.capacity());
    System.out.printf("* ch.size.....: %d%n", ch.size());
    ch.close();
  }
  
}
