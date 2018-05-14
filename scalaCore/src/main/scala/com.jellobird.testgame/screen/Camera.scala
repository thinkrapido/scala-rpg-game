package com.jellobird.testgame.screen

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.{Vector2, Vector3}
import com.jellobird.testgame.maps.PositionsRegistry.{GetDestination, SetDestination}
import com.jellobird.testgame.utils.world.Area

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * Created by jbc on 01.12.16.
  */
class Camera extends OrthographicCamera {

  var pos: ActorRef = null

  override def update(): Unit = {

    implicit val ec = ExecutionContext.Implicits.global
    implicit val timeout = Timeout(300.milliseconds)

    if (pos != null) {
      val f = ask(pos, GetDestination(null, "curr"))
        .mapTo[SetDestination]
        .map { curr =>
          val vec = new Vector3(curr.payload.asInstanceOf[Area].location.vec.scl(GameScreen.current.map.tilePixelWidth), 0)
          position.set(vec)
        }
      Await.result(f, 500.milliseconds) // wait to avoid flickering of player
    }

    super.update()
  }

}
