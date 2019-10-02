package com.thebeans.flyingtea


import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.thebeans.flyingtea.screens.GameScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.log.debug
import ktx.scene2d.Scene2DSkin


class FlyingTeaGame : KtxGame<KtxScreen>() {
    val batch by lazy {
        SpriteBatch()
    }

    val font by lazy {
        BitmapFont()
    }

    private val context = Context()

    override fun create() {
        Scene2DSkin.defaultSkin = Skin(Gdx.files.internal("skin/uiskin.json"))
        context.register {
            bindSingleton(this@FlyingTeaGame)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            bindSingleton(AssetManager())
            bindSingleton(PooledEngine())
        }
        addScreen(GameScreen())
        setScreen<GameScreen>()
        debug("Game"){
            "This is a great time woo!"
        }
        super.create()
    }

    override fun dispose() {
        context.dispose()
        super.dispose()
    }
}
