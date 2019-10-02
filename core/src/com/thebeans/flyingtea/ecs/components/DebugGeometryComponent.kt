package com.thebeans.flyingtea.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color

class DebugGeometryComponent(var width: Float, var height: Float, val color: Color = Color.WHITE): Component