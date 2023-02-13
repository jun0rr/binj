/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.type;

import com.jun0rr.binj.mapping.MethodNameAdapter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class MappedInvocationHandler implements InvocationHandler {
  
  private final Map<String,Object> map;
  
  public MappedInvocationHandler(Map<String,Object> m) {
    this.map = Objects.requireNonNull(m);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String meth = method.getName();
    if(meth.startsWith("get")) {
      meth = MethodNameAdapter.adapt(method);
    }
    return map.get(meth);
  }
  
  
  public static <T> T mapped(Class<T> cls, Map<String,Object> map) {
    return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new MappedInvocationHandler(map));
  }
  
  
  
  public static class InvocationException extends RuntimeException {
    
    public InvocationException(String msg) {
      super(msg);
    }
    
    public InvocationException(String msg, Object... args) {
      super(String.format(msg, args));
    }
    
    public InvocationException(Throwable cause) {
      super(cause);
    }
    
    public InvocationException(Throwable cause, String msg) {
      super(msg, cause);
    }
    
    public InvocationException(Throwable cause, String msg, Object... args) {
      super(String.format(msg, args), cause);
    }
    
  }
  
}
