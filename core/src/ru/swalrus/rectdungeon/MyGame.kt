package ru.swalrus.rectdungeon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import ru.swalrus.rectdungeon.Creatures.Dummy
import ru.swalrus.rectdungeon.Game.Chunk
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.UI.BottomPanel
import ru.swalrus.rectdungeon.UI.ItemButton
import ru.swalrus.rectdungeon.UI.StatusPanel

class MyGame : ApplicationAdapter() {

    lateinit var batch: SpriteBatch
    lateinit var chunk : Chunk
    lateinit var player: Player
    lateinit var topPanel: StatusPanel
    lateinit var bottomPanel: BottomPanel
    lateinit var listener: InputListener

    override fun create() {
        Const.loadImages()
        batch = SpriteBatch()

        // Define dungeon objects
        chunk = Chunk()
        player = Player(6, 2, 8, chunk.Center)
        var testEnemy = Dummy(3, 5, chunk.Center)

        listener = InputListener(player)
        topPanel = StatusPanel(player)
        bottomPanel = BottomPanel(player, listener)
        Gdx.input.inputProcessor = GestureDetector(listener)
    }

    override fun render() {
        Gdx.gl.glClearColor(1/16f, 1/16f, 1/16f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        // Draw room
        chunk.Center.render(batch)
        // Draw UI
        topPanel.draw(batch)
        bottomPanel.draw(batch)

        batch.end()
    }
}
