package com.jellobird.testgame.maps

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.Map._
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.utils.world.Area.Overlap
import com.jellobird.testgame.utils.world.{Area, Location, Range}

import scala.collection.JavaConverters._
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
  val collisionBoxes: List[Area] = tiledMap
      .getLayers
      .get(Layer.COLLISION_LAYER.toString)
      .getObjects.asScala
      .map(_.getProperties)
      .map(p => Area(
        Location(p.get("x").asInstanceOf[Float], p.get("y").asInstanceOf[Float]),
        Range(p.get("width").asInstanceOf[Float], p.get("height").asInstanceOf[Float])
      ))
      .map(_.scale(1.0f / tilePixelWidth))
      .toList

  def testCollision(box: Area): Boolean =
    collisionBoxes
      .map(_.relate(box) match {
        case Overlap(_, _) => true
        case _ => false
      })
      .exists(_ => true)

  def getCoordinatesFromMapObject(obj: MapObject): Vector2 = {
    val props = obj.getProperties
    val x = props.get("x").asInstanceOf[Float]
    val y = props.get("y").asInstanceOf[Float]
    new Vector2(x / tilePixelWidth, y / tilePixelHeight)
  }

  def startPosition: Vector2 = {
    val objects = tiledMap.getLayers().get(Layer.SPAWNS_LAYER.toString()).getObjects()
    getCoordinatesFromMapObject(objects.get("START"))
  }

  def getNearestSpawnPosition(ref: Vector2): Vector2 = {
    val iter = tiledMap.getLayers().get(Layer.SPAWNS_LAYER.toString()).getObjects().iterator()
    var out = new Vector2(width * width, height * height)
    if (iter.hasNext) {
      while(iter.hasNext) {
        val coords = getCoordinatesFromMapObject(iter.next())
        if (ref.dst(coords) < ref.dst(out)) out = coords
      }
    }
    else {
      out = ref
    }
    out
  }

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

