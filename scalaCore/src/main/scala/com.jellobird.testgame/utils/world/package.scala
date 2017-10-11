package com.jellobird.testgame.utils

import com.badlogic.gdx.math.Vector2

import scala.util.Try


/**
  * Created by Romeo Disca on 10/11/2017.
  */
package object world {

  implicit def Distance2Location(distance: Distance): Location =
    Location(
      (distance.distance * Math.sin(Math.PI - distance.rad)).toFloat,
      (distance.distance * Math.sin(distance.rad)).toFloat,
    )

  implicit def Distance2Range(distance: Distance): Range =
    Range(
      (distance.distance * Math.sin(Math.PI - distance.rad)).toFloat,
      (distance.distance * Math.sin(distance.rad)).toFloat,
    )

  implicit def Range2Location(range: Range): Location =
    Location(range.height, range.width)

  implicit def Range2Distance(range: Range): Distance =
    Distance(
      Try(Math.atan(range.height / range.width).toFloat).getOrElse(0),
      Math.sqrt(range.width * range.width + range.height * range.height).toFloat
    )

  implicit def Location2Range(location: Location): Range =
    Range(location.y, location.x)

  implicit def Location2Distance(location: Location): Distance =
    Distance(
      Try(Math.atan(location.y / location.x).toFloat).getOrElse(0),
      Math.sqrt(location.x * location.x + location.y * location.y).toFloat
    )

  implicit def Vector22Location(vec: Vector2): Location = Location(vec.x, vec.y)
  implicit def Location2Vector2(location: Location): Vector2 = new Vector2(location.x, location.y)

  implicit def Vector22Range(vec: Vector2): Range = Range(vec.x, vec.y)
  implicit def Range2Vector2(range: Range): Vector2 = new Vector2(range.x, range.y)

  implicit def Vector22Distance(vec: Vector2): Distance = Distance(vec.angleRad(), vec.dst2(Vector2.Zero))
  implicit def Distance2Vector2(distance: Distance): Vector2 = Location2Vector2(distance)

}
