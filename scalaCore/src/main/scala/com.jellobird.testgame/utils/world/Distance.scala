package com.jellobird.testgame.utils.world

import com.badlogic.gdx.math.Vector2

/**
  * Created by Romeo Disca on 10/11/2017.
  */
class Distance(vector: Vector2) extends Vector(vector) {

  def this(rad: Float, distance: Float) = this(((distance * Math.sin(Math.PI - rad)).toFloat, (distance * Math.sin(rad)).toFloat))

  def rad = vector angleRad
  def distance = vector len

  require(rad >= 0)
  require(rad < Math.PI * 2)
  require(distance >= 0)

  val deg = vector angle
  lazy val normal = new Distance(rad, 1)

}
