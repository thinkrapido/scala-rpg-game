package com.jellobird.testgame.maps

import akka.actor.Actor

/**
  * Created by jbc on 02.12.16.
  */
class LocationsRepositoryActor(val repo: LocationsRepository) extends Actor {
  import LocationsRepository._
  override def receive: Receive = {
    case RegisterEntity(payload) => repo.add(payload)
    case p @ AbsolutePos(_, uuid) => repo.find(uuid).get.handle(p)
    case p @ DeltaPos(_, uuid) => repo.find(uuid).get.handle(p)
    case _ =>
  }
}
