package ru.swalrus.rectdungeon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MyGame : ApplicationAdapter() {

    lateinit var batch: SpriteBatch
    lateinit var chunk : Chunk
    lateinit var player: Player

    override fun create() {
        Const.importImages()
        batch = SpriteBatch()

        chunk = Chunk()
        player = Player(2, 3, Const.images["HUMAN"]!!, chunk.Center)
    }

    override fun render() {
        Gdx.gl.glClearColor(1/16f, 1/16f, 1/16f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        chunk.Center.draw(batch)

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}
