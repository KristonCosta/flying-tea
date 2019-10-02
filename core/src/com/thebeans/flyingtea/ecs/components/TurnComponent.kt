package com.thebeans.flyingtea.ecs.components

import com.badlogic.ashley.core.Component

enum class Status {
    DONE,
    RUNNING,
    STARTED,
}
class TurnComponent(var status: Status, var awk: Boolean = false): Component