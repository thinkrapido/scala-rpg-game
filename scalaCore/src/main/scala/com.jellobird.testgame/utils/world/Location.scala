package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Location(val x: Float, val y: Float) extends Scale[Location] with Transform[Location] {

  override def scale(factor: Float) = Location(x * factor, y * factor)

  override def move(location: Location) = location

  override def moveBy(location: Location) = Location(this.x + location.x, this.y + location.y)

  override def rotate(rad: Float) = {
    val self: Distance = this
    self.rotate(rad)
  }

  override def transform(location: Location, factor: Float = 1) = moveBy(location).scale(factor)

}
