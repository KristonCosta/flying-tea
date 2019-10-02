package com.thebeans.flyingtea.map

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2

class Map(path: String) {
    val tiled: TiledMap = TmxMapLoader().load(path)
    val dimensions = {
        val layer = (tiled.layers.get(0) as TiledMapTileLayer)
        Vector2(layer.width.toFloat() * layer.tileWidth, layer.height.toFloat() * layer.tileHeight)
    }()
}