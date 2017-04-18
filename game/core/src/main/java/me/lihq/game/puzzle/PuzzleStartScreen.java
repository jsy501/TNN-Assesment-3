package me.lihq.game.puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.GameMain;
import me.lihq.game.screen.AbstractScreen;

public class PuzzleStartScreen extends AbstractScreen {
    private Stage stage;

    private final Label startLabel;
    private final Label rules;
    private TextButton startButton;
    private TextButton quitButton;
    private Skin skin = new Skin(
            Gdx.files.internal("assets/skin/skin.json"),
            new TextureAtlas(Gdx.files.internal("assets/skin/skin.atlas"))
    );
    private final String rule = "Your aim is to find all the match pair of the cards. \n" +
            "Click on the card to filp. \n" +
            "If the next card you click matched the previous one the cards will stay flipped\n" +
            "otherwise they will turn back again until you find they match. \n" +
            "The position of the cards won't change during the whole game. \n" +
            "There are 6 pairs in total.\n" +
            "\n" +
            "When you first clicked on the card, \n" +
            "the time will start to cound down. \n" +
            "Each time you click on card will be counted as one step. \n" +
            "Remember you only have limited time and steps to finish this puzzle.\n\n" +
            "Good Luck!";

    public PuzzleStartScreen(GameMain game){
        super(game);

        stage = new Stage(new FitViewport(GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/calibri.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = 20;
        BitmapFont font20 = generator.generateFont(p);
        p.size = 24;
        BitmapFont font24 = generator.generateFont(p);
        generator.dispose();

        startLabel = new Label("INSTRUCTION", new Label.LabelStyle(font24, Color.WHITE));
        startLabel.setPosition((PuzzleScreen.WIDTH - startLabel.getPrefWidth()) / 2, PuzzleScreen.HEIGHT-100);
        //startLabel.setY(Puzzle.HEIGHT - 200);
        stage.addActor(startLabel);


        rules = new Label(rule, new Label.LabelStyle(font20, Color.WHITE));
        rules.setPosition((PuzzleScreen.WIDTH - rules.getPrefWidth()) / 2, 300);
        stage.addActor(rules);

        startButton = new TextButton("START", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Start is clicked");
                game.setScreen(new PuzzleScreen(game));
            }
        });
        TextButton.TextButtonStyle style = startButton.getStyle();
        style.font = font24;
        startButton.setStyle(style);
        startButton.setX(300);
        startButton.setY(100);

        quitButton = new TextButton("QUIT", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("star screen quit is clicked");
                //TODO interface
            }
        });
        style = quitButton.getStyle();
        style.font = font24;
        quitButton.setStyle(style);
        quitButton.setX(PuzzleScreen.WIDTH-300);
        quitButton.setY(100);

        stage.addActor(quitButton);
        stage.addActor(startButton);
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


