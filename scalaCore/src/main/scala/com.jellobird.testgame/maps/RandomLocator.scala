package com.jellobird.testgame.maps
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.repositories.LocationsRepository.AbsolutePos
import com.jellobird.testgame.storage.Payload

/**
  * Created by jbc on 01.12.16.
  */
class RandomLocator(private val _map: Map) extends LocationEntity {

  override var _curr: Vector2 = new Vector2(_map.width / 2, _map.height / 2)
  override var _next: Vector2 = _curr

  override def handle[T](payload: Payload[T]) = {
    payload match {
      case AbsolutePos(vec, _) => _next = vec
    }
  }

}
