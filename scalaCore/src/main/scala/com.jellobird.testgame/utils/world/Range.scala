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

}
