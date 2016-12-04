package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.repositories.LocationsRepository.NewProxy
import com.jellobird.testgame.storage.{Payload, Storage}

/**
  * Created by jbc on 03.12.16.
  */
class ProxyLocator(private var proxy: LocationEntity, private val _map: Map) extends LocationEntity {
  override var _curr: Vector2 = new Vector2(_map.width / 2, _map.height / 2)
  override var _next: Vector2 = _curr

  override def handle[T](payload: Payload[T]) = {
    payload match {
      case NewProxy(uuid, _) => proxy = Storage.locations.find(uuid).get
    }
  }

  override protected def calcNextLocationAlgorithm(elapsed: Float): Unit = {
    _next = proxy._next
  }
}
