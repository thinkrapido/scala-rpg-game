package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Distance(rad: Float, distance: Float) extends Scale[Distance] with Transform[Distance] with Inverse[Distance] {

  require(rad >= 0)
  require(rad < Math.PI * 2)
  require(distance >= 0)

  val deg = rad * 180 / Math.PI
  lazy val normal = Distance(rad, 1)

  override def scale(factor: Float) = Distance(rad * factor, distance)

  override def move(distance: Distance) = this

  override def moveBy(distance: Distance) = {
    val self: Location = this
    val location: Location = distance
    Location(self.x + location.x, self.y + location.y)
  }

  override def rotate(rad: Float) = Distance(this.rad + rad, distance)

  override def transform(location: Location, factor: Float = 1) = moveBy(location).scale(factor)

  def addRad(rad: Float) = Distance(normalizeRad(this.rad + rad), distance)

  def inverse = addRad(Math.PI.toFloat)

  def matches(dist: Distance, radEpsilon: Float = 0, distanceEpsilon: Float = 0) =
    (Math.abs(rad - dist.rad) < radEpsilon) && (Math.abs(distance - dist.distance) < distanceEpsilon)

}
