package me.lihq.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import me.lihq.game.GameMain;
import me.lihq.game.gui.buttons.InventoryButton;
import me.lihq.game.gui.buttons.NpcNoteButton;
import me.lihq.game.gui.buttons.PersonalityMeterButton;
import me.lihq.game.Score;

/**
 * The status bar shown throughout the game
 * Contains UI controls for presenting the game status to the player
 */
class StatusBar extends Table
{
    /**
     * The height of the StatusBar
     */
    private final int HEIGHT = 50; //Used to set height of status bar

    /**
     * The amount of items that are in the StatusBar
     */
    private final int ITEM_COUNT = 4; //Used to set width of controls on bar

    /**
     * The width of the individual buttons of the StatusBar
     */
    private final int WIDTH = GameMain.GAME_WIDTH / ITEM_COUNT;

    private Gui gui;
    private Label scoreLabel;

    /**
     * The initializer for the StatusBar
     * Sets up UI controls and adds them to the stage ready for rendering
     */
    StatusBar(final GameMain game, Gui gui)
    {
        this.gui = gui;

        row().height(HEIGHT);
        defaults().width(WIDTH);

        scoreLabel = new Label("Score: 0", game.assetLoader.uiSkin, "half-tone");
        scoreLabel.setAlignment(Align.center, Align.center);
        add(scoreLabel).uniform();

        PersonalityMeterButton personalityMeterButton = new PersonalityMeterButton(game.assetLoader.uiSkin, gui);
        add(personalityMeterButton).uniform();

        InventoryButton inventoryButton = new InventoryButton(game.assetLoader.uiSkin, gui);
        add(inventoryButton).uniform();

        NpcNoteButton npcNoteButton = new NpcNoteButton(game.assetLoader.uiSkin, gui);
        add(npcNoteButton).uniform();
    }

    @Override
    public void act(float delta) {
        scoreLabel.setText("Score: " + gui.getGameWorld().getScore().getCurrentScore());
        super.act(delta);
    }
}
