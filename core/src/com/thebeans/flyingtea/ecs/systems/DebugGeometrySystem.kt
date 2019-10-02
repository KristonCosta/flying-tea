package com.thebeans.flyingtea.ecs.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.thebeans.flyingtea.ecs.components.DebugGeometryComponent
import com.thebeans.flyingtea.ecs.components.PositionComponent

class DebugGeometrySystem(private val renderer: ShapeRenderer): IteratingSystem(Family.all().get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val dbm = ComponentMapper.getFor(DebugGeometryComponent::class.java)

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val position = pm.get(entity)
        val geom = dbm.get(entity)
        renderer.set(ShapeRenderer.ShapeType.Line)
        renderer.color = geom.color
        renderer.rect(position.pos.x, position.pos.y, geom.width, geom.height)
    }
}