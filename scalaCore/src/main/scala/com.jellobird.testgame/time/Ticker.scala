package com.jellobird.testgame.time

import com.badlogic.gdx.utils.TimeUtils
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.utils.ScaleFactor

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.ExecutionContext

/**
  * Created by jbc on 23.12.16.
  */
case class Ticker(val name: String, val duration: FiniteDuration) extends Tick with ScaleFactor {

  private var _lastTimeTicked = 0L
  private var _nextTimeTicked = 0L

  implicit val ec = ExecutionContext.Implicits.global

  def start(f: => Unit): Unit = {
    Storage.actorSystem.scheduler.schedule(duration, duration) {
      f
      tick
    }
  }

  def tick {
    _lastTimeTicked = TimeUtils.nanoTime()
    _nextTimeTicked = TimeUtils.nanoTime() + duration.toNanos
    Storage.tickerRegistry ! this
  }
  def lastTimeTicked = _lastTimeTicked
  def nextTimeTimeTicked = _nextTimeTicked

  def factor: Float = {
    val curr = TimeUtils.nanoTime()
    1f * (curr - _lastTimeTicked) / (_nextTimeTicked - _lastTimeTicked)
  }

}
