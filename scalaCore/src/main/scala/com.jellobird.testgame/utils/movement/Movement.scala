package com.jellobird.testgame.utils.movement

/**
  * Created by Romeo Disca on 5/15/2018.
  */
case class Movement(velocity: Float, scaleFactor: Float = 1F) {
  def effectiveVelocity = velocity * scaleFactor;
}
