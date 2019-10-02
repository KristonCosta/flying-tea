package com.thebeans.flyingtea.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.thebeans.flyingtea.ecs.components.*
import com.thebeans.flyingtea.ecs.systems.AiSystem
import com.thebeans.flyingtea.ecs.systems.DebugGeometrySystem
import com.thebeans.flyingtea.ecs.systems.MoveToSystem
import com.thebeans.flyingtea.ecs.systems.TurnSystem
import com.thebeans.flyingtea.map.Map
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.ashley.entity
import ktx.log.debug
import ktx.scene2d.label
import ktx.scene2d.table
import kotlin.math.min


class GameScreen: KtxScreen, KtxInputAdapter {
    private val uiStage = Stage().apply {
        addActor(table {
            label("This is a test")
            setPosition(50f, 10f)
        })
    }
    private val map = Map("full_set.tmx")
    private val renderer = OrthogonalTiledMapRenderer(map.tiled)
    private val viewport = FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()).apply {
        (camera as OrthographicCamera).setToOrtho(false)
    }
    private val camera = viewport.camera as OrthographicCamera
    private val debugRenderer = ShapeRenderer()
    private val engine = PooledEngine()

    init {

        engine.apply {
            addSystem(DebugGeometrySystem(debugRenderer))
            addSystem(MoveToSystem())
            addSystem(AiSystem())
            addSystem(TurnSystem())
            addEntity(createEntity().apply {
                add(PositionComponent(Vector2(10f, 10f)))
                add(DebugGeometryComponent(width = 50f, height = 50f))
                add(AiComponent())
                add(TurnComponent(Status.STARTED))
            })
            addEntity(createEntity().apply {
                add(PositionComponent(Vector2(200f, 200f)))
                add(DebugGeometryComponent(width = 50f, height = 50f, color = Color.RED))
                add(AiComponent())
                add(TurnComponent(Status.DONE, awk = true))
            })
        }
    }

    override fun render(delta: Float) {
        handleInput()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        viewport.apply()
        renderer.setView(camera)
        renderer.render()

        debugRenderer.projectionMatrix = camera.combined
        debugRenderer.begin(ShapeRenderer.ShapeType.Line)
        engine.update(delta)
        debugRenderer.end()

        uiStage.viewport.apply()
        uiStage.act()
        uiStage.draw()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-4f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(4f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0f, 4f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0f, -4f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom += 0.1f
        }
        val width = viewport.worldWidth
        val height = viewport.worldHeight
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, min(map.dimensions.x / width, map.dimensions.y / height))
        val xzoom = width / 2.0f * camera.zoom
        val yzoom = height / 2.0f * camera.zoom
        camera.position.x = MathUtils.clamp(camera.position.x, xzoom, map.dimensions.x - xzoom)
        camera.position.y = MathUtils.clamp(camera.position.y, yzoom, map.dimensions.y - yzoom)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}