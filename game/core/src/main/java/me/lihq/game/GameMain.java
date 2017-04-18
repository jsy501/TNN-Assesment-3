/*
* This is the link to the executable jar file created from this project
*
* http://www.lihq.me/Downloads/Assessment2/Game.jar
*
* or visit http://www.lihq.me
* and click "Download Game"
 */

package me.lihq.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import me.lihq.game.gui.FadeInOut;
import me.lihq.game.gui.Gui;
import me.lihq.game.screen.*;

/**
 * EXTENDED
 * This is the class responsible for the game as a whole. It manages the current states and entry points of the game
 */
public class GameMain extends Game
{
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 750;

    /**
     * Asset container for referencing assetLoader throughout the game
     */
    public AssetLoader assetLoader;

    public MainMenuScreen mainMenuScreen;
    public PauseScreen pauseScreen;

    public GameScreen gameScreen;
    public PlayerSelectionScreen playerSelectionScreen;

    /**
     * used for screen transition effect
     */
    private Stage stage;
    public FadeInOut fadeInOut;


    /**
     * setScreen override for screen transition effect addition
     * @param screen
     */
    @Override
    public void setScreen(Screen screen) {
        fadeInOut.addAction(Actions.sequence(
                Actions.fadeIn(0.3f),
                Actions.run(() -> GameMain.super.setScreen(screen)),
                Actions.fadeOut(0.3f)
        ));

    }

    /**
     * This is called at start up. It initialises the game.
     */
    @Override
    public void create(){
        assetLoader = new AssetLoader();

        stage = new Stage();
        fadeInOut = new FadeInOut();
        stage.addActor(fadeInOut);

        //Set up the SplashScreen
        this.setScreen(new SplashScreen(this));
    }

    /**
     * This defines what's rendered on the screen for each frame.
     */
    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(); // This calls the render method of the screen that is currently set
        stage.act();
        stage.draw();
    }

    /**
     * This is to be called when you want to dispose of all data
     */
    @Override
    public void dispose()
    {
        assetLoader.dispose();
        stage.dispose();
    }

    /**
     * Overrides the getScreen method to return our AbstractScreen type.
     * This means that we can access the additional methods like update.
     *
     * @return The current screen of the game.
     */
    @Override
    public AbstractScreen getScreen()
    {
        return (AbstractScreen) super.getScreen();
    }

}
