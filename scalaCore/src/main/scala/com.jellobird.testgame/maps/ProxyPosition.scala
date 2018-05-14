package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.ActorRef
import akka.pattern.ask
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Storage
import PositionsRegistry.{GetDestination, SetDestination}
import akka.util.Timeout
import com.jellobird.testgame.utils.world.{Area, Location, Range}

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * Created by jbc on 03.12.16.
  */
class ProxyPosition(private var proxy: ActorRef, private val _map: Map) extends Position {

  override def receive: Receive = super.receive orElse {
    case SetDestination(_, "position", uuid: UUID) => Storage.positionsRegistry ! GetDestination(uuid, "position")
    case SetDestination(_, "position", actorRef: ActorRef) => proxy = actorRef
  }

  override def curr: Area = {
    implicit val ec = ExecutionContext.Implicits.global
    implicit val timeout = Timeout(300.milliseconds)

    val f = ask(proxy, GetDestination(null, "curr"))
      .mapTo[SetDestination]
      .map(curr => curr.payload.asInstanceOf[Area])
    Await.result(f, 200.milliseconds)
    //new Area(new Location(out), new Range(1, 1))
  }
}
