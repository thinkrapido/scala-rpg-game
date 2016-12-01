package com.jellobird.testgame

import akka.actor.{Actor, PoisonPill}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import ScalaBludBourne._

/**
  * Created by jbc on 26.11.16.
  */
class InputPoller extends Actor {

  import InputPoller._

  var counter = java.time.Instant.now().getNano()

  override def receive = {
    case Dispose =>
      self ! PoisonPill

    case Delay(nano) =>
    case _ =>
      val curr = java.time.Instant.now().getNano()
      val loopDurationDelay = curr - counter - (1000 / 60) * 1000000 - 1000000
      if (loopDurationDelay > 0) println("Delay %d".format(loopDurationDelay))
      counter = curr
  }

}

object InputPoller {
  case object Dispose
  case object Tick
  case class Delay(nano: Long)
}
