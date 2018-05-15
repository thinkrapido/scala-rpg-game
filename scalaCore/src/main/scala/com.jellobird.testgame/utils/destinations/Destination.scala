package com.jellobird.testgame.utils.destinations

import com.jellobird.testgame.utils.movement.Movement
import com.jellobird.testgame.utils.world.Location

/**
  * Created by Romeo Disca on 5/15/2018.
  */
trait Destination {

  def walkTowards(lm: (Location, Movement)): Location = lm._1

  protected[this] def clamp(current: Location, next: Location, calculated: Location): Location = {
    val distNext = next.copy.sub(current).distance
    val distCalculated = calculated.copy.sub(current).distance
    if (distNext > distCalculated) {
      calculated
    }
    else {
      next
    }
  }

  protected[this] def clamp(current: Location, next: Location, afterNext: Location, calculated: Location): Location = {
    val distNext = next.copy.sub(current).distance
    val distCalculated = calculated.copy.sub(current).distance
    if (distNext > distCalculated) {
      calculated
    }
    else {
      afterNext.copy.sub(next).normal.mul(calculated.copy.sub(next).distance)
    }
  }

}
