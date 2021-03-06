package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Range(width: Float, height: Float) extends Scale[Range] with Transform[Range] {

  require(width >= 0)
  require(height >= 0)

  val area = width * height

  override def scale(factor: Float) = Range(width * factor, height * factor)

  override def move(range: Range) = range

  override def moveBy(range: Range) = Range(this.width + range.width, this.height + range.height)

  override def rotate(rad: Float) = {
    val self: Distance = this
    self.rotate(rad)
  }

  override def transform(location: Location, factor: Float = 1) = moveBy(location).scale(factor)

  def matches(range: Range, epsilon: Float = 0) =
    (Math.abs(width - range.width) < epsilon) && (Math.abs(height - range.height) < epsilon)

}
