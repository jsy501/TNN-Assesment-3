package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.lihq.game.AssetLoader;
import me.lihq.game.GameMain;
import me.lihq.game.gui.Slot;
import me.lihq.game.people.Player;

/**
 * NEW
 * Screen for player selection for single player mode
 */

public class SinglePlayerSelectionScreen extends PlayerSelectionScreen {

    public SinglePlayerSelectionScreen(GameMain game) {
        super(game);
        selectionLabel = new Label("Choose your detective!", assetLoader.uiSkin, "title");

        selectionConfirmWindow = new Dialog("", game.assetLoader.uiSkin){
            @Override
            protected void result(Object object) {
                if (object.equals(true)){
                    playerOne = (Player) selectedPlayerSlot.getSlotActor();
                    game.singlePlayerGameScreen = new SinglePlayerGameScreen(game, playerOne);
                    game.setScreen(game.singlePlayerGameScreen);
                }
                else {
                    hide();
                }
            }
        };

        selectionConfirmWindow.button("OK", true);
        selectionConfirmWindow.button("Cancel", false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
