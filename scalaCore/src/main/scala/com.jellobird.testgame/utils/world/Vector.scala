package com.jellobird.testgame.utils.world

import com.badlogic.gdx.math.Vector2

/**
  * Created by Romeo Disca on 10/15/2017.
  */
class Vector(protected val vector: Vector2) {
  def copy = vector.cpy()

  def scale(factor: Float) = { vector.scl(factor); this }

  def move(other: Vector) = { vector.set(other.vector); this }

  def moveBy(other: Vector) = { vector.add(other.vector); this }

  def rotate(rad: Float) = { vector.setAngleRad(rad); this }
  def rotateBy(rad: Float) = { vector.rotateRad(rad); this }

  def transform(location: Location, factor: Float = 1) = { moveBy(location).scale(factor); this }

  def inverse = { vector.scl(-1); this }

  def matches(other: Vector, epsilon: Float = 0) = vector.epsilonEquals(other.vector, epsilon)

}

object Vector {
  implicit def Vector2Location(vector: Vector): Location = new Location(vector.vector)
  implicit def Vector2Rage    (vector: Vector): Range    = new Range   (vector.vector)
  implicit def Vector2Distance(vector: Vector): Distance = new Distance(vector.vector)
}