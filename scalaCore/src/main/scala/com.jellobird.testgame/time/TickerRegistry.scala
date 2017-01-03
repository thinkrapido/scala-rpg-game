package com.jellobird.testgame.time

import akka.actor.{Actor, Props}

import scala.collection.mutable

/**
  * Created by jbc on 23.12.16.
  */
class TickerRegistry extends Actor {

  import TickerRegistry._

  val map = new mutable.HashMap[String, mutable.Set[Tick]]()

  override def receive: Receive = {
    case Ticker(name, _) => map.getOrElse(name, mutable.Set()).foreach(_.tick)
    case RegisterTickerObserver(name, observer) =>
      map.get(name) match {
        case Some(set) => set.add(observer)
        case _ =>
          val set = mutable.Set[Tick]()
          map.put(name, set)
          set.add(observer)
      }
    case UnRegisterTickerObserver(name, observer) =>
      map.get(name) match {
        case Some(set) => set.remove(observer)
        case _ =>
      }
    case _ =>
  }
}

object TickerRegistry {

  def props = Props[TickerRegistry]

  case class RegisterTickerObserver(name: String, observer: Tick)
  case class UnRegisterTickerObserver(name: String, observer: Tick)

  class NoTickObservableRegistered(message: String = null, throwable: Throwable = null) extends RuntimeException(message, throwable)

}
