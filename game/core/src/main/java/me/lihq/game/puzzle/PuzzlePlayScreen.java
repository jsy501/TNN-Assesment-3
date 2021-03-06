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

public class PuzzlePlayScreen extends AbstractScreen {
    /**
     * Parameters needed for PuzzlePlayScreen:
     *
     * stepLabel - show the record of step
     * timeLabel - show the record of time left.
     * step - int used to calculate the steps
     * cardArray - to initialise the card that need to display in the puzzle
     * MAX_STEP - the max step the player can take to finished the puzzle
     * gameTime - record the time after the player start the game
     * MAX_TIME - the max time the player can take to finished puzzle
     * waitTime - time wait for card to close
     * lastCard - Each time two card will be clicked and checked. Last Card records the first clicked card
     * next - next records the second clicked card
     * waitForClose - if the card is flipped, check whether it need to be cloesd
     * gameStart - boolean show whether the puzzle is started
     */
    private Stage stage;

    private final int WIDTH = GameMain.GAME_WIDTH;
    private final int HEIGHT = GameMain.GAME_HEIGHT;

    private final Label stepLabel;
    private final Label timeLabel;
    private Array<Card> cardArray = new Array<>();
    private final int MAX_STEP = 20;
    private int step = MAX_STEP * 2;
    private float gameTime = 0;
    private final float MAX_TIME= 60;

    private float waitTime;

    private Card lastCard;
    private Card next;
    private boolean waitForClose;
    private boolean gameStart = false;


    public PuzzlePlayScreen(GameMain game) {
        /**
         * Initialise the GUI of Puzzle game, and add ClickListener to the card.
         * Game only starts after the player click a card for the first time
         */
        super(game);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        for (int i = 1; i <= 6; i++) {
            cardArray.add(new Card(i, game.assetLoader));
            cardArray.add(new Card(i, game.assetLoader));
        }
        cardArray.shuffle();
        Table table = new Table();
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

        timeLabel = new Label("Time Left: 60", game.assetLoader.uiSkin, "big");
        timeLabel.setX(WIDTH - 230);
        timeLabel.setY(HEIGHT - 400);
        stage.addActor(timeLabel);

        stepLabel = new Label("Step left: " + MAX_STEP, game.assetLoader.uiSkin, "big");
        stepLabel.setX(WIDTH - 230);
        stepLabel.setY(HEIGHT - 200);

        TextButton textButton = new TextButton("QUIT", game.assetLoader.uiSkin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Quit is clicked");
                game.setScreen(game.gameScreen);
            }
        });
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

        step--;
        stepLabel.setText("Step left: " + step / 2);

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
        game.setScreen(game.gameScreen);
        game.gameScreen.getCurrentGameWorld().puzzleSuccess();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        /**
         * Time is calculated each time of render
         * Wait for closed is checked every time of render
         */
        DecimalFormat df = new DecimalFormat("00");
        if (gameStart){
            gameTime += delta;
            float timeLeft = MAX_TIME - gameTime;
            if (timeLeft <= 0 || step < 0){
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
