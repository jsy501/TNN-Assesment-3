package me.lihq.game.gui.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import me.lihq.game.AssetLoader;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.Gui;

/**
 * Window that can display information that the player may need; string and/or actor
 */

public class InfoWindow extends GuiWindow {
    public InfoWindow(AssetLoader assetLoader, Gui gui, GameWorld gameWorld) {
        super("", assetLoader, gui, gameWorld);

        button("OK", true);
    }

    @Override
    protected void result(Object object) {
        if (object.equals(true)){
            hide();
        }
    }
}
