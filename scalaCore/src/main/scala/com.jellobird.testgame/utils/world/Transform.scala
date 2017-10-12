package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/12/2017.
  */
trait Transform[T] {

  this: Scale[T] =>

  def move(location: T): T
  def moveBy(location: T): T
  def rotate(rad: Float): T
  def transform(location: Location, factor: Float = 1): T
}
