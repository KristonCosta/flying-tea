package com.thebeans.flyingtea.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.thebeans.flyingtea.ecs.components.*
import ktx.ashley.mapperFor
import ktx.assets.pool

class AiSystem(): IteratingSystem(
        Family.all(PositionComponent::class.java, AiComponent::class.java, TurnComponent::class.java).get()) {
    private val pm = mapperFor<PositionComponent>()
    private val aim = mapperFor<AiComponent>()
    private val turnm = mapperFor<TurnComponent>()

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val position = pm.get(entity)
        val turn = turnm.get(entity)
        if (turn.status != Status.STARTED) {
            return
        }
        val endTurn = TurnComponent(Status.DONE)
        val moveDown = MoveToActionComponent(Vector2(0f, 0f).add(position.pos), 50f, "v", endTurn)
        val moveLeft = MoveToActionComponent(Vector2(0f, 100f).add(position.pos), 50f, "<", moveDown)
        val moveUp = MoveToActionComponent(Vector2(100f, 100f).add(position.pos), 50f, "^", moveLeft)
        val moveRight = MoveToActionComponent(Vector2(100f, 0f).add(position.pos), 50f, ">", moveUp)
        entity?.add(moveRight)
        turn.status = Status.RUNNING
    }
}