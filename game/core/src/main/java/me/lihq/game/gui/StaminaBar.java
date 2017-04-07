package me.lihq.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;

/**
 * NEW
 * GUI table for two player mode. contains player number, stamina and end turn button.
 */

public class StaminaBar extends Table{
    /**
     * PLAYER_NUM is 1 when the stamina bar is initialised the first time for player 1, 2 when initialised
     * for player 2
     */
    private static int PLAYER_NUM = 1;
    private final int BAR_WIDTH = 200;

    private GameWorld gameWorld;

    private ProgressBar staminaBar;

    public StaminaBar(GameMain game, GameWorld gameWorld){
        this.gameWorld = gameWorld;

        Label playerTurn = new Label("Player" + PLAYER_NUM++, game.assetLoader.uiSkin, "title");

        // reset back to 1 in case the player plays the two player mode again
        if (PLAYER_NUM > 2){
            PLAYER_NUM = 1;
        }

        staminaBar = new ProgressBar(0, 100, 0.5f, false, game.assetLoader.uiSkin);

        TextButton endTurnButton = new TextButton("End\nTurn", game.assetLoader.uiSkin);
        endTurnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.twoPlayerGameScreen.switchPlayer();
            }
        });

        add(playerTurn).padRight(70);
        add(staminaBar).width(BAR_WIDTH).padRight(10);
        add(endTurnButton);
    }

    @Override
    public void act(float delta) {
        staminaBar.setValue(gameWorld.getPlayer().getStamina().getCurrentStamina());
        super.act(delta);
    }
}
