package ru.swalrus.rectdungeon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import ru.swalrus.rectdungeon.Game.Chunk
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.UI.BottomPanel
import ru.swalrus.rectdungeon.UI.InventoryRenderer
import ru.swalrus.rectdungeon.UI.ItemCard
import ru.swalrus.rectdungeon.UI.StatusPanel

class MyGame : ApplicationAdapter() {

    lateinit var batch: SpriteBatch
    lateinit var chunk : Chunk
    lateinit var player: Player
    lateinit var topPanel: StatusPanel
    lateinit var bottomPanel: BottomPanel
    lateinit var listener: InputListener
    lateinit var inventoryRenderer: InventoryRenderer
    lateinit var card: ItemCard

    override fun create() {
        Const.load()
        batch = SpriteBatch()

        // Define dungeon objects
        chunk = Chunk()
        player = Player(6, 2, 8, chunk.Center)

        // TODO: listener.* = * -> *.init { lister.* = this }
        listener = InputListener(player)
        card = ItemCard(player, listener)
        listener.card = card
        inventoryRenderer = InventoryRenderer(player, card)
        listener.inventory = inventoryRenderer
        topPanel = StatusPanel(player)
        bottomPanel = BottomPanel(player, listener, inventoryRenderer)
        listener.bottomPanel = bottomPanel
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
        inventoryRenderer.draw(batch)
        card.draw(batch)

        batch.end()
    }
}
