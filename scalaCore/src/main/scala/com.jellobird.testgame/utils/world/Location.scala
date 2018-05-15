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

  override def normal = new Location(vector nor)
  override def copy = new Location(vec)
  override def add(other: Vector): Vector = new Location(vec.add(other.vec))
  override def sub(other: Vector): Vector = new Location(vec.sub(other.vec))
  override def mul(factor: Float): Vector = new Location(vec.scl(factor))
}

object Location {
  def fromPolar(rad: Float, distance: Float) = new Location(((distance * Math.sin(Math.PI - rad)).toFloat, (distance * Math.sin(rad)).toFloat))
}