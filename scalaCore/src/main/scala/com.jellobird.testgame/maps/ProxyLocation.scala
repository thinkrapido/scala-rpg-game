package com.jellobird.testgame.maps

import akka.actor.ActorRef
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.registry.LocationRegistry.Get

/**
  * Created by jbc on 03.12.16.
  */
class ProxyLocation(private val proxy: ActorRef, private val _map: Map) extends Location {
  _curr = new Vector2(_map.width / 2, _map.height / 2)
  _next = null

  override protected def calcNextLocationAlgorithm(elapsed: Float): Unit = {
    super.calcNextLocationAlgorithm(elapsed)
    proxy ! Get(null, "curr")
  }

}
