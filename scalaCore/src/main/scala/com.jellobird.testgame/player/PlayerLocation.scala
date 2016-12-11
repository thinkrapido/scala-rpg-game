package com.jellobird.testgame.player

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.Location
import com.jellobird.testgame.storage.registry.LocationsRegistry.SetLocation

/**
  * Created by jbc on 03.12.16.
  */
class PlayerLocation(val startPosition: Vector2) extends Location {
  _curr = startPosition
  _next = _curr

  override def receive: Receive = super.receive orElse {
    case SetLocation(_, "deltaNext", payload: Vector2) => _next = _next.mulAdd(payload, .05f)
  }
}
