package com.jellobird.testgame.utils.world

import com.badlogic.gdx.math.Vector2

/**
  * Created by Romeo Disca on 10/15/2017.
  */
abstract class Vector(protected val vector: Vector2) {
  def scale(factor: Float) = { vector.scl(factor); this }

  def moveTo(other: Vector) = { vector.set(other.vector); this }

  def moveBy(other: Vector) = { vector.add(other.vector); this }
  def rotateTo(rad: Float) = { vector.setAngleRad(rad); this }

  def rotateBy(rad: Float) = { vector.rotateRad(rad); this }
  def transform(other: Vector, factor: Float = 1) = { moveBy(other).scale(factor); this }

  def inverse = { vector.scl(-1); this }

  def matches(other: Vector, epsilon: Float = 0) = vector.epsilonEquals(other.vector, epsilon)

  def rad = vector angleRad

  val deg = vector angle
  def distance = vector len

  def normal: Vector
  def copy: Vector
  def vec: Vector2 = vector.cpy()

  override def hashCode(): Int = vector.hashCode()
  override def equals(obj: scala.Any): Boolean = vector.equals(obj)
}

object Vector {
  //  implicit def Vector22Vector (vec: Vector2): Vector = new Vector(vec)
  implicit def Vector2Vector2 (vector: Vector): Vector2  = vector.vector
  implicit def Vector2Location(vector: Vector): Location = new Location(vector.vector)
  implicit def Vector2Rage    (vector: Vector): Range    = new Range   (vector.vector)
}