package me.lihq.game.gui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import me.lihq.game.AssetLoader;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.Gui;
import me.lihq.game.puzzle.PuzzleStartScreen;

/**
 * NEW
 * Window that displays yes/no prompt window for mini game for secret room and get user response
 */

public class PromptWindow extends GuiWindow{
    public PromptWindow(AssetLoader assetLoader, Gui gui, GameWorld gameWorld) {
        super("", assetLoader, gui, gameWorld);

        button("Yes", true);
        button("No", false);
    }

    @Override
    protected void result(Object object) {
        if (object.equals(true)){
            gameWorld.game.setScreen(new PuzzleStartScreen(gameWorld.game));
        }
        hide();
    }
}
