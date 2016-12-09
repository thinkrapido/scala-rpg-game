package com.jellobird.testgame.maps

import akka.actor.ActorRef
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.registry.LocationRegistry.{Get, Process, Set}

/**
  * Created by jbc on 03.12.16.
  */
class ProxyLocation(private val proxy: ActorRef, private val _map: Map) extends Location {
  _curr = new Vector2(_map.width / 2, _map.height / 2)
  _next = null

  override def receive: Receive = super.receive orElse {
    case Process(_, "update", _) => proxy forward Get(null, "currAsNext")
    case Process(_, "calcNextLocation", elapsed: Float) => calcNextLocation(elapsed)
  }
}
