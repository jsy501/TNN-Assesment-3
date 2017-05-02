package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;
import me.lihq.game.screen.elements.GameClearMenu;
import me.lihq.game.screen.elements.MainMenu;

/**
 * Screen for game clear. Displays completed time and score, and option to retry or quit.
 */

public class GameClearScreen  extends AbstractScreen{
    private Stage stage;

    private GameClearMenu menu;

    public GameClearScreen(GameMain game, GameWorld gameWorld) {
        super(game);

        stage = new Stage(new FitViewport(GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT));

        //Creates a MainMenu object thus creating the main menu
        menu = new GameClearMenu(game, gameWorld);
    }

    @Override
    public void show() {
        game.assetLoader.endingTune.play();
        game.assetLoader.endingTune.setLooping(true);

        stage.addActor(menu);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        game.assetLoader.endingTune.play();
    }

    @Override
    public void hide() {
        game.assetLoader.endingTune.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
