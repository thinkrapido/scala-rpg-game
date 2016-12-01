package com.jellobird.testgame

/**
  * Created by jbc on 29.11.16.
  */
package object utils {

  implicit def javaIteratorToWrapper[T](iter: java.util.Iterator[T]): IteratorWrapper[T] = new IteratorWrapper[T](iter)

}
