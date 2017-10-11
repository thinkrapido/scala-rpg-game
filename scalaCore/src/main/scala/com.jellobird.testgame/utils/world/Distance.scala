package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Distance(rad: Float, distance: Float) extends Scale[Distance] {

  val deg = rad * 180 / Math.PI

  override def scale(factor: Float) = Distance(rad * factor, distance)

}
