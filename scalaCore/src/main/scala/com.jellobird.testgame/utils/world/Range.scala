package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Range(width: Float, height: Float) extends Scale[Range] {

  val area = width * height

  override def scale(factor: Float) = Range(width * factor, height * factor)

}
