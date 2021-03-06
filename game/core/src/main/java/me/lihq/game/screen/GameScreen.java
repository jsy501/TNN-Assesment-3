package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;

import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.Gui;
import me.lihq.game.people.PersonState;
import me.lihq.game.people.controller.PlayerController;

import java.util.stream.IntStream;

/**
 * NEW
 * Superclass for two game screens; single and two player mode
 */

public abstract class GameScreen extends AbstractScreen{
    private float footStepSoundInterval;

    protected PlayerController currentController;
    protected GameWorld currentGameWorld;
    protected Gui currentGui;

    public GameScreen(GameMain game) {
        super(game);
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(currentGui.getGuiStage());
        multiplexer.addProcessor(currentController);
        Gdx.input.setInputProcessor(multiplexer);

        currentGameWorld.getTime().setPaused(false);
        if (game.assetLoader.menuMusic.isPlaying()) {
            IntStream.range(0, 1000000).forEachOrdered(n -> game.assetLoader.menuMusic.setVolume(n/1000000));
        }
        game.assetLoader.menuMusic.stop();
        game.assetLoader.roomTone.play();
        game.assetLoader.roomTone.setLooping(true);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (game.pauseScreen == null) {
                game.pauseScreen = new PauseScreen(game, this);
            }
            game.setScreen(game.pauseScreen);
        }

        if (currentGameWorld.isGameClear() || Gdx.input.isKeyJustPressed(Input.Keys.T)){
            currentGameWorld.setGameClear(false);
            game.setScreen(new GameClearScreen(game, currentGameWorld));
        }

        // when the player is walking, plays footstep sound for every 0.2 seconds to be in sync with walking
        if (currentGameWorld.getPlayer().getState() == PersonState.WALKING){
            if (footStepSoundInterval > 0.2f){
                game.assetLoader.footstep.play(0.3f);
                footStepSoundInterval = 0;
            }
            footStepSoundInterval+=delta;
        }

        currentGameWorld.render(delta);
        currentGui.render(delta);
    }

    /**
     * This is called when the window is resized
     *
     * @param width  - The new width
     * @param height - The new height
     */

    @Override
    public void resize(int width, int height) {
        currentGameWorld.getGameWorldStage().getViewport().update(width, height);
        currentGui.getGuiStage().getViewport().update(width, height);
    }

    @Override
    public void resume() {
        game.assetLoader.roomTone.play();
    }

    @Override
    public void hide() {
        game.assetLoader.roomTone.stop();
    }

    public GameWorld getCurrentGameWorld() {
        return currentGameWorld;
    }

    public Gui getCurrentGui() {
        return currentGui;
    }
}
