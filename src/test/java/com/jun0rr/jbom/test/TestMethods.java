/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestMethods {
  
  public static class A {
    public int get1() {
      return 1;
    }
    public int getX(int x) {
      return x;
    }
    public void getY() {}
  }
  
  @Test
  public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, Throwable {
    A a = new A();
    Method get = List.of(A.class.getDeclaredMethods()).stream()
        .filter(m->m.getName().startsWith("get"))
        .filter(m->m.getReturnType() != void.class)
        .filter(m->m.getParameterCount() == 0)
        .findAny().get();
    Method set = List.of(A.class.getDeclaredMethods()).stream()
        .filter(m->m.getName().startsWith("get"))
        .filter(m->m.getReturnType() != void.class)
        .filter(m->m.getParameterCount() == 1)
        .findAny().get();
    System.out.println("get: " + get);
    System.out.println("set: " + set);
    MethodType getType = MethodType.methodType(get.getReturnType());
    MethodType setType = MethodType.methodType(set.getReturnType(), set.getParameterTypes());
    Lookup lo = MethodHandles.publicLookup();
    MethodHandle getHandle = lo.findVirtual(get.getDeclaringClass(), get.getName(), getType);
    MethodHandle setHandle = lo.findVirtual(set.getDeclaringClass(), set.getName(), setType);
    System.out.println("invoke: " + (int)setHandle.invokeExact(a, (int)getHandle.invokeExact(a)));
    //int i = (int)handle.invokeExact(a);
    //System.out.println(meth + ".invoke: " + (String)handle.invokeExact(a));
  }
  
}
