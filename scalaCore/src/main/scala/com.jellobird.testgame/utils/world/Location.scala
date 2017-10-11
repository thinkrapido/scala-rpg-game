package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Location(val x: Float, val y: Float) extends Scale[Location] {
  override def scale(factor: Float) = Location(x * factor, y * factor)
}
