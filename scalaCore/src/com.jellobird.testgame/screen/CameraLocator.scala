package com.jellobird.testgame.screen

import com.jellobird.testgame.maps.{LocationEntity, ProxyLocator}
import com.jellobird.testgame.maps.Map

/**
  * Created by jbc on 03.12.16.
  */
class CameraLocator(proxy: LocationEntity, _map: Map) extends ProxyLocator(proxy, _map) {
}
