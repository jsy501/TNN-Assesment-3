package me.lihq.game.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import me.lihq.game.GameMain;
import me.lihq.game.people.Player;

/**
 * NEW
 * Screen for player selection for two player mode
 */

public class TwoPlayerSelectionScreen extends PlayerSelectionScreen{
    public TwoPlayerSelectionScreen(GameMain game) {
        super(game);

        selectionLabel = new Label("Choose detective for Player 1!", assetLoader.uiSkin, "title");

        selectionConfirmWindow = new Dialog("", game.assetLoader.uiSkin){
            @Override
            protected void result(Object object) {
                if (object.equals(true)){
                    if (playerOne == null){
                        playerOne = (Player) selectedPlayerSlot.getSlotActor();
                        selectionLabel.setText("Choose detective for Player 2!");
                        selectedPlayerSlot.remove();
                    }
                    else{
                        playerTwo = (Player) selectedPlayerSlot.getSlotActor();
                        game.twoPlayerGameScreen = new TwoPlayerGameScreen(game, playerOne, playerTwo);
                        game.setScreen(game.twoPlayerGameScreen);
                    }
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
