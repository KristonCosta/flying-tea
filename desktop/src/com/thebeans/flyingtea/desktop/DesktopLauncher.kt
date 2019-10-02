package com.thebeans.flyingtea.desktop

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.thebeans.flyingtea.FlyingTeaGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 640
        config.height = 480
        config.forceExit = true
        LwjglApplication(FlyingTeaGame(), config).logLevel = Application.LOG_DEBUG
    }
}
