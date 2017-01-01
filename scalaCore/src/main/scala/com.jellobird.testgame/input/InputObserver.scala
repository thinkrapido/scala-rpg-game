package com.jellobird.testgame.input

import akka.actor.ActorRef
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.time.Ticker

import scala.collection.immutable.HashSet
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


/**
  * Created by jbc on 02.12.16.
  */
class InputObserver {

  import InputObserver._

  private var actors = HashSet[ActorRef]()
  def addListener(actor: ActorRef) = actors = actors + actor
  def removeListener(actor: ActorRef) = actors = actors - actor
  def notifyActors: Unit = {
    val recentEvents = InputKeyState.keyStates.filter(_.count > 0).map(_.copy)
    if(recentEvents.size > 0) {
      InputKeyState.reset
      actors.foreach{ _ ! new KeyEvents(recentEvents) }
    }
  }

  implicit val ec = ExecutionContext.global
  Storage.actorSystem.scheduler.schedule(1.seconds, POLL_INTERVALL.duration) {
    InputKeyState.keyStates.foreach(_.++)
  }
  Storage.actorSystem.scheduler.schedule(1.seconds, NOTIFY_INTERVALL.duration) {
    notifyActors
    NOTIFY_INTERVALL.tick
  }

  def clear: Unit = actors = HashSet[ActorRef]()

}

object InputObserver {

  val POLL_INTERVALL = new Ticker("input:keys:poll", 3.milliseconds)
  val NOTIFY_INTERVALL = new Ticker("input:keys:notify", 100.milliseconds)

}