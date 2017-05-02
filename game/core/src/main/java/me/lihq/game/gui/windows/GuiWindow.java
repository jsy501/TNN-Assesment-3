package me.lihq.game.gui.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import me.lihq.game.AssetLoader;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.Gui;

/**
 * EXTENDED, changed passing parameter from skin to assetloader for sound asset
 * Basic window used by gui. Interacts with a gui and gameworld.
 */

abstract class GuiWindow extends Dialog {
    protected AssetLoader assetLoader;
    public Gui gui;
    GameWorld gameWorld;

    GuiWindow(String title, AssetLoader assetLoader, Gui gui, GameWorld gameWorld) {
        super(title, assetLoader.uiSkin);

        this.assetLoader = assetLoader;
        this.gui = gui;
        this.gameWorld = gameWorld;

        getTitleLabel().setAlignment(Align.center);
        setMovable(false);
        setModal(true);

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                assetLoader.menuClick.play();
            }
        });
    }
}

