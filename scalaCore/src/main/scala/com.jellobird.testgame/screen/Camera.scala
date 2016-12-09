package com.jellobird.testgame.screen

import akka.actor.ActorRef
import akka.pattern.ask
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.registry.LocationRegistry.Get

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by jbc on 01.12.16.
  */
class Camera extends OrthographicCamera {

  var location: ActorRef = null

  override def update(): Unit = {

    implicit val ec = ExecutionContext.Implicits.global

    if (location != null) {
      ask(location, Get(null, "curr"))(300.milliseconds)
        .mapTo[Vector2]
        .map(v => position.set(v, 0))
    }

    super.update()
  }

}
