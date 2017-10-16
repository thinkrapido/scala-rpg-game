package com.jellobird.testgame.utils

import com.badlogic.gdx.math.Vector2

import scala.util.Try


/**
  * Created by Romeo Disca on 10/11/2017.
  */
package object world {

  implicit def Tuple2Location(tuple: (Float, Float)) = new Location(new Vector2(tuple._1, tuple._2))
  implicit def Tuple2Range(tuple: (Float, Float)) = new Range(new Vector2(tuple._1, tuple._2))
  implicit def Tuple2Vector2(tuple: (Float, Float)) = new Vector2(tuple._1, tuple._2)

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
