package com.jellobird.testgame.utils.world

import com.badlogic.gdx.math.Vector2

import scala.util.Try

/**
  * Created by Romeo Disca on 10/11/2017.
  */
class Location(vector: Vector2) extends Vector(vector) {

  def this(x: Float, y: Float) = this((x, y))

  def x = vector.x
  def y = vector.y

}
