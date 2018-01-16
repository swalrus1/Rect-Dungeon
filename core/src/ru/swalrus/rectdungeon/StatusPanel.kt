package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class StatusPanel (player: Player) {

    private val AP_img: Array<Texture> = arrayOf(
            Const.images["AP_0"]!!, Const.images["AP_1"]!!, Const.images["AP_2"]!!)
    private var player: Player = player
    private val SCREEN_HEIGHT: Int = Const.SCREEN_HEIGHT
    private val SCREEN_WIDTH: Int = Const.SCREEN_WIDTH
    private val PANEL_HEIGHT: Float = Const.IMG_SIZE * Const.UI_SCALE
    private val heart: Texture = Const.images["HEART"]!!
    private val heart_empty: Texture = Const.images["HEART_EMPTY"]!!
    private val tile: Texture = Const.images["UI_TILE_TOP"]!!
    private val menu_button: Texture = Const.images["MENU_BUTTON"]!!
    private val HP_MARGIN = Const.HP_MARGIN

    fun draw(batch: SpriteBatch) {

        // Draw AP status
        batch.draw(AP_img[player.AP], 0f, SCREEN_HEIGHT - PANEL_HEIGHT,
                PANEL_HEIGHT, PANEL_HEIGHT, 0f, 1f, 1f, 0f)

        // Draw border
        for (i in 1 until Const.ROOM_SIZE + 4) {
            batch.draw(tile, i * PANEL_HEIGHT, SCREEN_HEIGHT - PANEL_HEIGHT,
                    PANEL_HEIGHT, PANEL_HEIGHT, 0f, 1f, 1f, 0f)
        }

        // Draw HP
        for (i in 0 until player.maxHP) {
            if (player.HP > i) {
                batch.draw(heart, i * PANEL_HEIGHT / 2 + PANEL_HEIGHT +
                        (i + 1) * HP_MARGIN, SCREEN_HEIGHT - PANEL_HEIGHT * 3 / 4,
                        PANEL_HEIGHT / 2, PANEL_HEIGHT / 2, 0f, 1f, 1f, 0f)
            } else {
                batch.draw(heart_empty,i * PANEL_HEIGHT / 2 + PANEL_HEIGHT +
                        (i + 1) * HP_MARGIN,SCREEN_HEIGHT - PANEL_HEIGHT * 3 / 4,
                        PANEL_HEIGHT / 2, PANEL_HEIGHT / 2, 0f, 1f, 1f, 0f)
            }
        }

        // Draw menu button
        batch.draw(menu_button, SCREEN_WIDTH - PANEL_HEIGHT, SCREEN_HEIGHT - PANEL_HEIGHT,
                PANEL_HEIGHT, PANEL_HEIGHT, 0f, 1f, 1f, 0f)
    }
}