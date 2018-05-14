package com.jellobird.testgame.utils.world

import com.badlogic.gdx.math.Vector2

import scala.util.Try

/**
  * Created by Romeo Disca on 10/11/2017.
  */
class Range(vector: Vector2) extends Vector(vector) {

  def this(width: Float, height: Float) = this((width, height))

  def width = vector.x
  def height = vector.y

  require(width >= 0)
  require(height >= 0)

  val area = width * height

  override def normal = new Range(vector nor)
  override def copy = new Range(vec)
}

object Range {
  def fromPolar(rad: Float, distance: Float) = new Range(((distance * Math.sin(Math.PI - rad)).toFloat, (distance * Math.sin(rad)).toFloat))
}