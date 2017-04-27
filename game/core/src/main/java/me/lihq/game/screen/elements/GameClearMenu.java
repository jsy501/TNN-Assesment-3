package me.lihq.game.screen.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;
import me.lihq.game.Score;
import me.lihq.game.screen.SinglePlayerSelectionScreen;

/**
 * EXTENDED
 * Table that contains contents for game clear screen
 */

public class GameClearMenu extends MenuTable{

    /**
     * Constructor for the menu
     *
     * @param game  - The game object the menu is being loaded for
     */
    public GameClearMenu(GameMain game, GameWorld gameWorld) {
        super(game.assetLoader.uiSkin, "GAME CLEAR!");

        TextButton mainMenu = new TextButton("Main Menu", menuSkin, "menu");

        TextButton quit = new TextButton("Quit", menuSkin, "menu");

        int totalTime = (int) gameWorld.getTime().getTotalTime();

        //convert seconds into minutes and seconds
        String timeString = String.valueOf(totalTime/60) + ":" + String.valueOf(totalTime%60);

        int totalScore = gameWorld.getScore().getFinalScore(totalTime);
        Label timeLabel = new Label("Time taken: " + timeString, menuSkin, "title", Color.WHITE);
        Label scoreLabel = new Label("Score: " + totalScore, menuSkin, "title", Color.WHITE);
        Label highScoreLabel = new Label("New Highscore!", menuSkin, "title", Color.RED);

        contentTable.add(timeLabel).row();
        contentTable.add(scoreLabel).row();
        if (gameWorld.getScore().isHighScore(totalScore)) {
            contentTable.add(highScoreLabel).row();
        }

        addButton(mainMenu);
        addButton(quit);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.mainMenuScreen);
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
}
