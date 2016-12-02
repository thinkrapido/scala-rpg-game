package com.jellobird.testgame.input

import akka.actor.ActorRef
import com.badlogic.gdx.InputProcessor
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.storage.Storage

import scala.collection.immutable.HashSet
import scala.concurrent.ExecutionContext

/**
  * Created by jbc on 02.12.16.
  */
class InputObserver extends InputProcessor {

  import scala.concurrent.duration._

  private var actors = HashSet[ActorRef]()
  def addListener(actor: ActorRef) = actors = actors + actor
  def removeListener(actor: ActorRef) = actors = actors - actor
  def notifyActors: Unit = {
    val recentEvents = InputKeyState.keyStates.filter(_.count > 0).map(_.copy)
    if ( recentEvents.size > 0) actors.foreach{ _ ! new KeyEvents(recentEvents) }
  }

  implicit val ec = ExecutionContext.global
  Storage.actorSystem.scheduler.schedule(1.seconds, 3.milliseconds) {
    collectInputs
  }

  def collectInputs: Unit = InputKeyState.keyStates.foreach(_.++)

  def reset: Unit = InputKeyState.keyStates.foreach(_.!)

  override def keyTyped(character: Char): Boolean = false

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  override def keyDown(keycode: Int): Boolean = { notifyActors ; reset ; true }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean =  { notifyActors ; reset ; true }

  override def scrolled(amount: Int): Boolean = false

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

}
