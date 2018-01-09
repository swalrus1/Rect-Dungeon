package ru.swalrus.rectdungeon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Creatures.*

class MyGame : ApplicationAdapter() {

    lateinit var batch: SpriteBatch
    lateinit var chunk : Chunk
    lateinit var player: Player

    override fun create() {
        Const.importImages()
        batch = SpriteBatch()

        chunk = Chunk()
        player = Player(6, 2, Const.images["HUMAN"]!!, chunk.Center)
        var testEnemy = Dummy(3, 5, chunk.Center)
        var testEnemy2 = Dummy(4, 1, chunk.Center)
        Gdx.input.inputProcessor = GestureDetector(InputListener(player))
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
