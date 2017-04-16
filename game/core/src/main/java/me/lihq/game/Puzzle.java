package me.lihq.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import me.lihq.game.screen.PuzzleEndScreen;
import me.lihq.game.screen.PuzzleScreen;

import java.text.DecimalFormat;

public class Puzzle extends Stage {
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    private final PuzzleScreen screen;
    private final Label stepLabel;
    private final Label timeLabel;
    private int step = 0;
    private Array<Card> cardArray = new Array<Card>();
    private Boolean isClear = false;
    private final int maxStep = 22;
    private float gameTime = 0;
    private final float maxGameTime= 60;

    private Table table = new Table();
    private TextButton textButton;

    private Json json = new Json();
    private JsonValue cardData;
    private float waitTime;

    public Puzzle(PuzzleScreen puzzleScreen) {
        screen = puzzleScreen;
        Array<JsonValue> list = json.fromJson(Array.class, Gdx.files.internal("cards.json"));
        for (JsonValue v : list) {
            cardArray.add(json.readValue(Card.class, v));
            cardArray.add(json.readValue(Card.class, v));
        }
        cardArray.shuffle();
        for (final Card card : cardArray) {
            if(cardArray.indexOf(card,true) % 4 ==0){
                table.row();
            }
            table.add(card)
                    .padRight(10)
                    .padBottom(10);
            card.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gameStart = true;
                    Puzzle.this.clickCard(card);

                }

            });
        }

        table.setX(50);
        table.setY(HEIGHT-50);
        table.align(Align.topLeft);
        addActor(table);

        Skin skin = new Skin(
                Gdx.files.internal("skin/skin.json"),
                new TextureAtlas(Gdx.files.internal("skin/skin.atlas"))
        );

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("calibri.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = 24;
        BitmapFont font = generator.generateFont(p);
        generator.dispose();

        timeLabel = new Label("Time Left: 60", new Label.LabelStyle(font, Color.WHITE));
        timeLabel.setX(WIDTH - 230);
        timeLabel.setY(HEIGHT - 400);
        addActor(timeLabel);

        stepLabel = new Label("Step: 0", new Label.LabelStyle(font, Color.WHITE));
        stepLabel.setX(WIDTH - 230);
        stepLabel.setY(HEIGHT - 200);

        textButton = new TextButton("QUIT", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Quit is clicked");
                //TODO interface
            }
        });
        textButton.getStyle().font = font;
        textButton.setX(WIDTH  - 230);
        textButton.setY(HEIGHT - 600);

        addActor(textButton);

        addActor(stepLabel);
    }

    private Card lastCard;
    private Card next;
    boolean waitForClose;
    boolean gameStart = false;




    /**
     * This function deals with the logic of flipping cards.
     * The card will be unable to flip if it is under
     * The game will finish if the player get all card flipped or the time run out.
     */
    private void clickCard(Card card) {
        /**
         * The card will be unable to flip if it is wait for closing or the card is already been flipped.
         */
        if (waitForClose || card == lastCard || card == next || card.getIsFilp())
            return;

        step++;
        stepLabel.setText("Step: " + step);

        if (lastCard == null){
            card.setIsFilp(true);
            lastCard = card;
        } else {
            if( card.isMatch(lastCard)){
                card.setIsFilp(true);
                lastCard = null;
                next = null;
            } else {
                card.setIsFilp(true);
                next = card;
                waitForClose = true;
                waitTime = 0;
            }
        }

        for (Card c : cardArray) {
            if (!c.getIsFilp())
                return;

        }
        isClear = true;
        PuzzleGame.instance.setScreen(new PuzzleEndScreen("COMPLETE"));
        Runnable callback = screen.getCallback();
        if (callback != null) callback.run();

    }

    public boolean checkClear (){
        return isClear;
    }

    @Override
    public void act(float delta) {
        DecimalFormat df = new DecimalFormat("00");
        super.act(delta);
        if (gameStart){
            gameTime += delta;
            float timeLeft = maxGameTime - gameTime;
            if (timeLeft <= 0 || step >= maxStep){
                timeLeft=0;
                gameStart = false;
                PuzzleGame.instance.setScreen(new PuzzleEndScreen("GAMEOVER"));
            }
            timeLabel.setText("Time left: "+ df.format(timeLeft) );
        }

        if (waitForClose) {
            waitTime += delta;

            if (waitTime > 1) {
                next.setIsFilp(false);
                lastCard.setIsFilp(false);
                lastCard = null;
                waitForClose = false;
                next = null;
            }
        }
    }
}
