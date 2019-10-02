package com.thebeans.flyingtea.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class MoveToActionComponent(
        val goal: Vector2,
        val speed: Float,
        val name: String,
        var next: Component?): Component