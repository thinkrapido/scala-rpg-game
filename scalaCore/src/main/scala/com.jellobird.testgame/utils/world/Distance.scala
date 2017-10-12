package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Distance(rad: Float, distance: Float) extends Scale[Distance] with Transform[Distance] {

  val deg = rad * 180 / Math.PI

  override def scale(factor: Float) = Distance(rad * factor, distance)

  override def move(distance: Distance) = this

  override def moveBy(distance: Distance) = {
    val self: Location = this
    val location: Location = distance
    Location(self.x + location.x, self.y + location.y)
  }

  override def rotate(rad: Float) = Distance(this.rad + rad, distance)

  override def transform(location: Location, factor: Float = 1) = moveBy(location).scale(factor)

}
