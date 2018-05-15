package com.jellobird.testgame.utils.destinations

import com.jellobird.testgame.utils.movement.Movement
import com.jellobird.testgame.utils.world.Location

/**
  * Created by Romeo Disca on 5/15/2018.
  */
class Route extends Destination {

  private[this] var path: List[Location] = List()

  override def walkTowards(lm: (Location, Movement)): Location = path match {
    case next :: tail => {
      val (location, movement) = lm;
      val direction = next.copy.sub(location).normal;
      val calculatedLocation = direction.mul(movement.effectiveVelocity)
      path = tail
      tail match {
        case head :: _ => clamp(location, next, head, calculatedLocation)
        case Nil => clamp(location, next, calculatedLocation)
      }
    }
    case Nil => super.walkTowards(lm);
  }

  def set(path: List[Location]): Route = { this.path = path ; this }
  def append(location: Location): Route = { path = path.:+(location) ; this }
  def append(path: List[Location]): Route = { this.path = this.path.++:(path) ; this }

}
