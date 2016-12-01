package com.jellobird.testgame.utils

/**
  * Created by jbc on 29.11.16.
  */
class IteratorWrapper[A](iter: java.util.Iterator[A]) {
  def foreach(f: A => Unit) = while(iter.hasNext) { f(iter.next) }
}
