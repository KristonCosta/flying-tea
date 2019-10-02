package com.thebeans.flyingtea.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.thebeans.flyingtea.ecs.components.AiComponent
import com.thebeans.flyingtea.ecs.components.PositionComponent
import com.thebeans.flyingtea.ecs.components.Status
import com.thebeans.flyingtea.ecs.components.TurnComponent
import ktx.ashley.mapperFor

class TurnSystem(): IteratingSystem(
        Family.all(TurnComponent::class.java).get()) {
    private val turnm = mapperFor<TurnComponent>()

    override fun processEntity(entity: Entity?, deltaTime: Float) {
    }

    override fun update(deltaTime: Float) {
        val entitiesIndex = entities.size() - 1
        for (i in 0 until entities.size()) {
            val turn = turnm.get(entities.get(i))
            if (turn.status == Status.DONE && !turn.awk) {
                turn.awk = true
                if (i == entitiesIndex) {
                    entities.get(0).add(TurnComponent(Status.STARTED))
                } else {
                    entities.get(i + 1).add(TurnComponent(Status.STARTED))
                }
                return
            }
        }
    }
}