package com.thebeans.flyingtea.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.thebeans.flyingtea.ecs.components.MoveToActionComponent
import com.thebeans.flyingtea.ecs.components.PositionComponent
import ktx.ashley.mapperFor
import kotlin.math.pow
import ktx.ashley.*
import ktx.assets.pool

import ktx.log.debug

class MoveToSystem(): IteratingSystem(
        Family.all(PositionComponent::class.java, MoveToActionComponent::class.java).get()) {
    private val vecPool = pool { Vector2() }
    private val pm = mapperFor<PositionComponent>()
    private val mvtm = mapperFor<MoveToActionComponent>()

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val position = pm.get(entity)
        val moveAction = mvtm.get(entity)
        val goalCopy = vecPool.obtain().set(moveAction.goal)
        val positionCopy = vecPool.obtain().set(position.pos)
        val direction = goalCopy.sub(positionCopy).setLength(1f)
        positionCopy.add(direction.scl(moveAction.speed * deltaTime))
        position.pos.set(positionCopy)

        if (positionCopy.sub(moveAction.goal).len2() < 0.1) {
            position.pos.set(moveAction.goal)
            val next = moveAction.next
            entity?.remove<MoveToActionComponent>()
            next?.apply {
                entity?.add(this)
            }
        }
        vecPool.free(goalCopy)
        vecPool.free(positionCopy)
    }
}