package com.jellobird.testgame.maps

import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}
import Map._
import com.jellobird.testgame.storage.Storage
/**
  * Created by jbc on 27.11.16.
  */
abstract class Map(val currentMap: MapEnum.Value) {

  val tiledMap: TiledMap = Storage.assetManager.getMapAsset(currentMap.toString())

  private val _properties = tiledMap.getLayers().get(0).asInstanceOf[TiledMapTileLayer]
  val width: Int = _properties.getWidth
  val height: Int = _properties.getHeight
  val tilePixelWidth: Float = _properties.getTileWidth
  val tilePixelHeight: Float = _properties.getTileHeight
  val pixelWidth: Float = width * tilePixelWidth
  val pixelHeight: Float = height * tilePixelHeight


}

object Map {

  object MapEnum extends Enumeration {
    val TOPWORLD = Value("maps/topworld.tmx")
    val TOWN = Value("maps/town.tmx")
    val CASTLE_OF_DOOM = Value("maps/castle_of_doom.tmx")
  }

  object TileLayer extends Enumeration {
    val BACKGROUND_LAYER = Value("Background_Layer")
    val GROUND_LAYER = Value("Ground_Layer")
    val DECORATION_LAYER = Value("Decoration_Layer")
  }

  object Layer extends Enumeration {
    val COLLISION_LAYER = Value("MAP_COLLISION_LAYER")
    val SPAWNS_LAYER = Value("MAP_SPAWNS_LAYER")
    val PORTAL_LAYER = Value("MAP_PORTAL_LAYER")
    val QUEST_ITEM_SPAWN_LAYER = Value("MAP_QUEST_ITEM_SPAWN_LAYER")
    val QUEST_DISCOVER_LAYER = Value("MAP_QUEST_DISCOVER_LAYER")
    val ENEMY_SPAWN_LAYER = Value("MAP_ENEMY_SPAWN_LAYER")
    val PARTICLE_EFFECT_SPAWN_LAYER = Value("PARTICLE_EFFECT_SPAWN_LAYER")
  }

  object LightLayer extends Enumeration {
    val LIGHTMAP_DAWN_LAYER = Value("MAP_LIGHTMAP_LAYER_DAWN")
    val LIGHTMAP_AFTERNOON_LAYER = Value("MAP_LIGHTMAP_LAYER_AFTERNOON")
    val LIGHTMAP_DUSK_LAYER = Value("MAP_LIGHTMAP_LAYER_DUSK")
    val LIGHTMAP_NIGHT_LAYER = Value("MAP_LIGHTMAP_LAYER_NIGHT")
  }

}

