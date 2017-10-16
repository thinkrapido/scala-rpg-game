package com.jellobird.testgame.utils

import com.badlogic.gdx.math.Vector2

import scala.util.Try


/**
  * Created by Romeo Disca on 10/11/2017.
  */
package object world {

  implicit def Tuple2Vector(tuple: (Float, Float)) = new Vector(new Vector2(tuple._1, tuple._2))
  implicit def Tuple2Vector2(tuple: (Float, Float)) = new Vector2(tuple._1, tuple._2)

  implicit def Vector22Location(vec: Vector2): Location = new Location(vec)
  implicit def Location2Vector2(location: Location): Vector2 = location copy

  implicit def Vector22Range(vec: Vector2): Range = new Range(vec)
  implicit def Range2Vector2(range: Range): Vector2 = range copy

  implicit def Vector22Distance(vec: Vector2): Distance = new Distance(vec)
  implicit def Distance2Vector2(distance: Distance): Vector2 = distance copy

  def ratio(numerator: Float, denominator: Float): Option[Float] = Try(numerator / denominator).toOption
  def normalizeRad(rad: Float): Float = {
    if (rad > Math.PI * 2) {
      normalizeRad((rad - Math.PI * 2).toFloat)
    }
    else if (rad < 0) {
      normalizeRad((rad + Math.PI * 2).toFloat)
    }
    else {
      rad
    }
  }
}
