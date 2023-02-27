package com.jun0rr.binj.impl;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class Rethrow<R extends RuntimeException> {

  @FunctionalInterface
  public static interface ThrowableFunction<A,B> {
    public B apply(A a) throws Throwable;
  }

  @FunctionalInterface
  public static interface ThrowableBiFunction<A,B,C> {
    public C apply(A a, B b) throws Throwable;
  }

  @FunctionalInterface
  public static interface ThrowableConsumer<A> {
    public void accept(A a) throws Throwable;
  }

  @FunctionalInterface
  public static interface ThrowablePredicate<A> {
    public boolean test(A a) throws Throwable;
  }

  @FunctionalInterface
  public static interface ThrowableBiConsumer<A,B> {
    public void accept(A a, B b) throws Throwable;
  }

  @FunctionalInterface
  public static interface ThrowableSupplier<A> {
    public A get() throws Throwable;
  }

  @FunctionalInterface
  public static interface ThrowableRun {
    public void run() throws Throwable;
  }


  private final Function<Throwable,R> fun;

  public Rethrow(Function<Throwable,R> fun) {
    this.fun = Objects.requireNonNull(fun);
  }

  public static <S extends RuntimeException> Rethrow<S> of(Function<Throwable,S> f) {
    return new Rethrow(f);
  }

  public static Rethrow<IllegalArgumentException> ofIllegalArg() {
    return of(t->new IllegalArgumentException(t));
  }

  public static Rethrow<IllegalStateException> ofIllegalState() {
    return of(t->new IllegalStateException(t));
  }

  public static Rethrow<RuntimeException> ofRuntime() {
    return of(t->new RuntimeException(t));
  }

  public static <C> C run(ThrowableSupplier<C> s) {
    return ofRuntime().call(s);
  }

  public static void run(ThrowableRun r) {
    ofRuntime().call(r);
  }
  
  public static <D,E> Function<D,E> function(ThrowableFunction<D,E> f) {
    return d->Rethrow.run(()->f.apply(d));
  }

  public static <D,E,F> BiFunction<D,E,F> bifunction(ThrowableBiFunction<D,E,F> f) {
    return (d,e)->run(()->f.apply(d, e));
  }

  public static <D> Supplier<D> supplier(ThrowableSupplier<D> f) {
    return ()->run(()->f.get());
  }

  public static <D> Consumer<D> consumer(ThrowableConsumer<D> f) {
    return d->run(()->f.accept(d));
  }

  public static <D> Predicate<D> predicate(ThrowablePredicate<D> f) {
    return d->run(()->f.test(d));
  }

  public static <D,E> BiConsumer<D,E> biconsumer(ThrowableBiConsumer<D,E> f) {
    return (d,e)->run(()->f.accept(d, e));
  }

  public <A> A call(ThrowableSupplier<A> s) {
    try {
      return s.get();
    }
    catch(Throwable e) {
      throw fun.apply(e);
    }
  }

  public void call(ThrowableRun r) {
    try {
      r.run();
    }
    catch(Throwable e) {
      throw fun.apply(e);
    }
  }

}