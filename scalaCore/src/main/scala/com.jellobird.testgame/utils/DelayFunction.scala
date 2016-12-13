package com.jellobird.testgame.utils

import akka.actor.Cancellable
import com.badlogic.gdx.utils.TimeUtils
import com.jellobird.testgame.storage.Storage

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by jbc on 13.12.16.
  */
class DelayFunction(val f: () => Unit, val delay: Duration) {

  implicit val ec = ExecutionContext.global

  private var timer: Long = 0
  private var cancelable: Cancellable = null

  def renew =
    if (cancelable == null || cancelable.isCancelled) start
    else timer = TimeUtils.nanoTime()

  def start = {
    timer = TimeUtils.nanoTime()
    cancelable = Storage.actorSystem.scheduler.schedule(100.nanoseconds, 100.nanoseconds){
      if (TimeUtils.nanoTime() - timer > delay.toNanos) {
        f()
        cancelable.cancel()
      }
    }
  }

}
