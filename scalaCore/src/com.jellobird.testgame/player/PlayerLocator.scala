package com.jellobird.testgame.player

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.LocationEntity
import com.jellobird.testgame.storage.Payload
import com.jellobird.testgame.storage.repositories.LocationsRepository.{AbsolutePos, DeltaPos}

/**
  * Created by jbc on 03.12.16.
  */
class PlayerLocator(val startPosition: Vector2) extends LocationEntity {
  override var _curr: Vector2 = startPosition
  override var _next: Vector2 = _curr

  override def handle[T](payload: Payload[T]) = {
    payload match {
      case AbsolutePos(vec, _) => _next = vec
      case DeltaPos(vec, _) => _next = _next.add(vec)
    }
  }
}
