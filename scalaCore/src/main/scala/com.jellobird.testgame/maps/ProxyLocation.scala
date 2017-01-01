package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.ActorRef
import akka.pattern.ask
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Storage
import LocationsRegistry.{GetDestination, SetDestination}
import akka.util.Timeout

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * Created by jbc on 03.12.16.
  */
class ProxyLocation(private var proxy: ActorRef, private val _map: Map) extends Location {

  override def receive: Receive = super.receive orElse {
    case SetDestination(_, "location", uuid: UUID) => Storage.locationsRegistry ! GetDestination(uuid, "location")
    case SetDestination(_, "location", actorRef: ActorRef) => proxy = actorRef
  }

  override def curr: Vector2 = {
    implicit val ec = ExecutionContext.Implicits.global
    implicit val timeout = Timeout(300.milliseconds)

    val f = ask(proxy, GetDestination(null, "curr"))
      .mapTo[SetDestination]
      .map(curr => curr.payload.asInstanceOf[Vector2])
    val out = Await.result(f, 200.milliseconds)
    //println(out)
    out
  }
}
