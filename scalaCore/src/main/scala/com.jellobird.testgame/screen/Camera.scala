package com.jellobird.testgame.screen

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.registry.LocationRegistry.{Get, Set}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by jbc on 01.12.16.
  */
class Camera extends OrthographicCamera {

  var location: ActorRef = null

  override def update(): Unit = {

    implicit val ec = ExecutionContext.Implicits.global
    implicit val timeout = Timeout(300.milliseconds)

    if (location != null) {
      ask(location, Get(null, "curr"))
        .mapTo[Set]
        .map(set => position.set(set.payload.asInstanceOf[Vector2], 0))
    }

    super.update()
  }

}
