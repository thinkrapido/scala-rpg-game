package com.jellobird.testgame.player

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.Location
import com.jellobird.testgame.maps.LocationsRegistry.SetDestination

/**
  * Created by jbc on 03.12.16.
  */
class PlayerLocation(val startPosition: Vector2) extends Location {

  override def receive: Receive = super.receive orElse {
    case SetDestination(_, "oneStepFurther", payload: Vector2) => oneStepFurther(payload)
  }

  def oneStepFurther(direction: Vector2): Unit = {
    setDestination(direction.nor().scl(currStep).add(curr))
  }

}
