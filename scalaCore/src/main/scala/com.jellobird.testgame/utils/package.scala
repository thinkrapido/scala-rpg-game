package com.jellobird.testgame

/**
  * Created by jbc on 29.11.16.
  */
package object utils {

  class IteratorWrapper[A](iter: java.util.Iterator[A]) {
    def foreach(f: A => Unit) = while(iter.hasNext) { f(iter.next) }
  }

  implicit def javaIteratorToWrapper[T](iter: java.util.Iterator[T]): IteratorWrapper[T] = new IteratorWrapper[T](iter)

}
