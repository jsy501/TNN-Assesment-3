package me.lihq.game.puzzle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.GameMain;
import me.lihq.game.screen.AbstractScreen;

import java.text.DecimalFormat;

public class PuzzleScreen extends AbstractScreen {
    private Stage stage;

    public static int WIDTH = GameMain.GAME_WIDTH;
    public static int HEIGHT = GameMain.GAME_HEIGHT;

    private final Label stepLabel;
    private final Label timeLabel;
    private int step = 0;
    private Array<Card> cardArray = new Array<>();
    private boolean isClear = false;
    private final int maxStep = 22;
    private float gameTime = 0;
    private final float maxGameTime= 60;

    private Table table = new Table();
    private TextButton textButton;

    private float waitTime;

    private Card lastCard;
    private Card next;
    boolean waitForClose;
    boolean gameStart = false;


    public PuzzleScreen(GameMain game) {
        super(game);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        for (int i = 1; i <= 6; i++) {
            cardArray.add(new Card(i, game.assetLoader));
            cardArray.add(new Card(i, game.assetLoader));
        }
        cardArray.shuffle();
        for (final Card card : cardArray) {
            if(cardArray.indexOf(card,true) % 4 ==0){
                table.row();
            }
            table.add(card)
                    .padRight(10)
                    .padBottom(10)
                    .size(150, 210);
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameStart = true;
                    clickCard(card);
                }
            });
        }

        table.setX(50);
        table.setY(HEIGHT-50);
        table.align(Align.topLeft);
        stage.addActor(table);

        Skin skin = new Skin(
                Gdx.files.internal("assets/skin/skin.json"),
                new TextureAtlas(Gdx.files.internal("assets/skin/skin.atlas"))
        );

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/calibri.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = 24;
        BitmapFont font = generator.generateFont(p);
        generator.dispose();

        timeLabel = new Label("Time Left: 60", new Label.LabelStyle(font, Color.WHITE));
        timeLabel.setX(WIDTH - 230);
        timeLabel.setY(HEIGHT - 400);
        stage.addActor(timeLabel);

        stepLabel = new Label("Step: 0", new Label.LabelStyle(font, Color.WHITE));
        stepLabel.setX(WIDTH - 230);
        stepLabel.setY(HEIGHT - 200);

        textButton = new TextButton("QUIT", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Quit is clicked");
                game.setScreen(game.gameScreen);
            }
        });
        textButton.getStyle().font = font;
        textButton.setX(WIDTH  - 230);
        textButton.setY(HEIGHT - 600);

        stage.addActor(textButton);

        stage.addActor(stepLabel);
    }

    /**
     * This function deals with the logic of flipping cards.
     * The card will be unable to flip if it is under
     * The game will finish if the player get all card flipped or the time run out.
     */
    private void clickCard(Card card) {
        /**
         * The card will be unable to flip if it is wait for closing or the card is already been flipped.
         */
        if (waitForClose || card == lastCard || card == next || card.isFlip())
            return;

        step++;
        stepLabel.setText("Step: " + step);

        if (lastCard == null){
            card.setFlip(true);
            lastCard = card;
        } else {
            if( card.isMatch(lastCard)){
                card.setFlip(true);
                lastCard = null;
                next = null;
            } else {
                card.setFlip(true);
                next = card;
                waitForClose = true;
                waitTime = 0;
            }
        }

        for (Card c : cardArray) {
            if (!c.isFlip())
                return;

        }
        isClear = true;
        game.setScreen(game.gameScreen);
        game.gameScreen.getCurrentGameWorld().puzzleSuccess();
    }

    public boolean checkClear (){
        return isClear;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        DecimalFormat df = new DecimalFormat("00");
        if (gameStart){
            gameTime += delta;
            float timeLeft = maxGameTime - gameTime;
            if (timeLeft <= 0 || step >= maxStep){
                timeLeft=0;
                gameStart = false;
                game.setScreen(game.gameScreen);
                game.gameScreen.getCurrentGameWorld().puzzleFail();
            }
            timeLabel.setText("Time left: "+ df.format(timeLeft) );
        }

        if (waitForClose) {
            waitTime += delta;

            if (waitTime > 1) {
                next.setFlip(false);
                lastCard.setFlip(false);
                lastCard = null;
                waitForClose = false;
                next = null;
            }
        }

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
