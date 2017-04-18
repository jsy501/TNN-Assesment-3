package me.lihq.game.screen.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import me.lihq.game.GameMain;
import me.lihq.game.Score;
import me.lihq.game.screen.SinglePlayerSelectionScreen;
import me.lihq.game.screen.TwoPlayerSelectionScreen;

/**
 * EXTENDED
 * Main Menu UI table
 */

public class MainMenu extends MenuTable
{

    /**
     * Constructor for the menu
     *
     * @param game - The game object the menu is being loaded for
     */
    public MainMenu(GameMain game) {
        super(game.assetLoader.menuSkin, "Murder Mystery Game!");

        TextButton singlePlayerButton = new TextButton("Single Player", menuSkin);

        TextButton twoPlayerButton = new TextButton("Two Player", menuSkin);

        TextButton quit = new TextButton("Quit", menuSkin);

        Label highScoreLabel = new Label("High score: " + Gdx.app.getPreferences("pref").getInteger("highScore", 0), menuSkin, "default", Color.RED);
        contentTable.add(highScoreLabel);

        //Loading the buttons onto the stage
        addButton(singlePlayerButton);
        addButton(twoPlayerButton);
        addButton(quit);

        //Making the "Single Player" button clickable and causing it to start the game
        singlePlayerButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.playerSelectionScreen = new SinglePlayerSelectionScreen(game);
                game.setScreen(game.playerSelectionScreen);
            }
        });

        twoPlayerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playerSelectionScreen = new TwoPlayerSelectionScreen(game);
                game.setScreen(game.playerSelectionScreen);
            }
        });

        //Making the "Quit" button clickable and causing it to close the game
        quit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
    }


}