package com.jellobird.testgame.storage

import java.util.concurrent.TimeUnit

import com.badlogic.gdx.utils.TimeUtils

/**
  * Created by jbc on 01.12.16.
  */
final class Timestamp() {
  val timestamp: Long = TimeUtils.nanoTime()
}
