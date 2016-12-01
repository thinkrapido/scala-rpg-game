package com.jellobird.testgame.storage

import scala.collection.mutable

/**
  * Created by jbc on 01.12.16.
  */
trait Repository[T <: Entity] {

  private[this] val container = new mutable.HashMap[String, T]()

  final def find(uuid: String): Option[T] = container.get(uuid)

  final def all(): List[T] = container.values.toList

  final def add[E <: T](entity: E): Option[T]  = container.put(entity.UUID, entity)

  final def remove(uuid: String): Option[T] = container.remove(uuid)

}
