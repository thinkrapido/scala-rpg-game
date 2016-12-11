package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.ActorRef
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.registry.LocationsRegistry.{GetLocation, SetLocation}

/**
  * Created by jbc on 03.12.16.
  */
class ProxyLocation(private var proxy: ActorRef, private val _map: Map) extends Location {
  _curr = new Vector2(_map.width / 2, _map.height / 2)
  _next = null

  override protected def calcNextLocationAlgorithm(elapsed: Float): Unit = {
    super.calcNextLocationAlgorithm(elapsed)
    proxy ! GetLocation(null, "curr")
  }

  override def receive: Receive = super.receive orElse {
    case SetLocation(_, "location", uuid: UUID) => Storage.locations ! GetLocation(uuid, "location")
    case SetLocation(_, "location", actorRef: ActorRef) => proxy = actorRef
  }

}
