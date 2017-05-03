package me.lihq.game.puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.GameMain;
import me.lihq.game.screen.AbstractScreen;

/**
 * The PuzzleStartScreen holds the GUI of the start menu.
 */

public class PuzzleStartScreen extends AbstractScreen {
    /**
     *  Puzzle start menu elements of Start screen.
     */
    private Stage stage;

    private final String rule =
            "Your aim is to find all the match pairs of the cards. \n" +
            "Click on the card to flip.\n" +
            "If the next card you click matched the previous one \n" +
            "the cards will stay flipped\n" +
            "otherwise they will turn back again until you find they match. \n" +
            "The position of the cards won't change during the whole game. \n" +
            "There are 6 pairs in total.\n" +
            "\n" +
            "When you first clicked on the card, \n" +
            "the time will start to count down. \n" +
            "Each time you click on card will be counted as one step. \n" +
            "Remember you only have limited time and steps \n" + "to finish this puzzle.\n\n" +
            "Good Luck!";

    /**
     * The constructor initialised all elements need for the start screen.
     * Table is used to display textButton and label in the right place.
     * @param game this provides access to the gameMain class so that screens can set the states of the game.
     */

    public PuzzleStartScreen(GameMain game){
        super(game);

        stage = new Stage(new FitViewport(GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT));

        Table table = new Table();
        table.setFillParent(true);

        Label startLabel = new Label("INSTRUCTION", game.assetLoader.uiSkin, "big");
        table.add(startLabel).padTop(50).colspan(2).row();


        Label ruleLabel = new Label(rule, game.assetLoader.uiSkin, "big");
        table.add(ruleLabel).expand().colspan(2).row();

        TextButton startButton = new TextButton("START", game.assetLoader.uiSkin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Start is clicked");
                game.setScreen(new PuzzlePlayScreen(game));
            }
        });

        TextButton quitButton = new TextButton("QUIT", game.assetLoader.uiSkin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("star screen quit is clicked");
                game.setScreen(game.gameScreen);
            }
        });

        table.add(startButton).padBottom(50);
        table.add(quitButton).padBottom(50);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}


