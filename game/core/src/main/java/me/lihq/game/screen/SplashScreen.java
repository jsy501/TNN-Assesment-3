package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.GameMain;

/**
 * Splash screen. The game assets are loaded during splash.
 */

public class SplashScreen extends AbstractScreen{
    private Animation<TextureRegion> loremIpsomSplashAnimation;
    private Actor loremIpsomImage;
    private Actor teamNoNameImage;

    private Stage stage;

    private float stateTime = 0;

    private boolean inputBlock = false;

    /**
     * This constructor sets the relevant properties of the class.
     *
     * @param game this provides access to the gameMain class so that screens can set the states of the game.
     */
    public SplashScreen(GameMain game) {
        super(game);

        stage = new Stage(new FitViewport(GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT));
    }

    @Override
    public void show() {
        game.assetLoader.loadSplashAssets();
        game.assetLoader.getManager().finishLoading();
        game.assetLoader.assignSplashAssets();
        game.assetLoader.menuMusic.setLooping(true);
        game.assetLoader.menuMusic.play();

        loremIpsomSplashAnimation = new Animation<>(0.5f, game.assetLoader.loremIpsomSplash.getRegions());
        loremIpsomImage = new Actor(){
            @Override
            public void act(float delta) {
                super.act(delta);
                stateTime += delta;
            }

            @Override
            public void draw(Batch batch, float parentAlpha) {
                Color color = batch.getColor();
                batch.setColor(getColor());

                TextureRegion currentFrame = loremIpsomSplashAnimation.getKeyFrame(stateTime, true);
                batch.draw(currentFrame, 0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);

                batch.setColor(color);
            }
        };

        teamNoNameImage = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                Color color = batch.getColor();
                batch.setColor(getColor());

                batch.draw(game.assetLoader.teamNoNameSplash, getX(), getY());

                batch.setColor(color);
            }
        };
        teamNoNameImage.setSize(game.assetLoader.teamNoNameSplash.getWidth(), game.assetLoader.teamNoNameSplash.getHeight());
        teamNoNameImage.setPosition(GameMain.GAME_WIDTH/2, GameMain.GAME_HEIGHT/2, Align.center);
        teamNoNameImage.addAction(Actions.alpha(0));

        stage.addActor(loremIpsomImage);
        stage.addActor(teamNoNameImage);

        loremIpsomImage.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.fadeOut(0.5f)
        ));
        teamNoNameImage.addAction(Actions.sequence(
                Actions.delay(2.5f),
                Actions.fadeIn(0.5f),
                Actions.delay(2f),
                Actions.run(() -> {
                    System.out.println("action");
                    game.assetLoader.assignGameAssets();

                    game.mainMenuScreen = new MainMenuScreen(game);
                    game.setScreen(game.mainMenuScreen);
                })
        ));

        game.assetLoader.loadGameAssets();
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();

        if (game.assetLoader.getManager().update()){
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && !inputBlock){
                inputBlock = true;

                teamNoNameImage.clearActions();
                System.out.println("skip");
                game.assetLoader.assignGameAssets();

                game.mainMenuScreen = new MainMenuScreen(game);
                game.setScreen(game.mainMenuScreen);
            }
        }

    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose()
    {
        stage.dispose();
    }
}
