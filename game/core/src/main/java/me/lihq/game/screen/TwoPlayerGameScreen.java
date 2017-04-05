package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.Gui;
import me.lihq.game.people.Player;
import me.lihq.game.people.controller.PlayerController;


public class TwoPlayerGameScreen extends AbstractScreen{
    private PlayerController playerOneController;
    private PlayerController playerTwoController;
    private PlayerController currentController;

    private GameWorld gameWorldOne;
    private GameWorld gameWorldTwo;
    private GameWorld currentGameWorld;

    private Gui guiOne;
    private Gui guiTwo;
    private Gui currentGui;

    private boolean isPlayerOneTurn = true;

    public TwoPlayerGameScreen(GameMain game, Player playerOne, Player playerTwo) {
        super(game);

        playerOne.getStamina().enable();
        game.gameWorldOne = new GameWorld(game, playerOne);
        this.gameWorldOne = game.gameWorldOne;

        playerTwo.getStamina().enable();
        game.gameWorldTwo = new GameWorld(game, playerTwo);
        this.gameWorldTwo = game.gameWorldTwo;

        game.guiOne = new Gui(game, gameWorldOne, true);
        this.guiOne = game.guiOne;

        game.guiTwo = new Gui(game, gameWorldTwo, true);
        this.guiTwo = game.guiTwo;

        gameWorldOne.setGui(guiOne);
        gameWorldTwo.setGui(guiTwo);

        playerOneController = new PlayerController(gameWorldOne);
        playerTwoController = new PlayerController(gameWorldTwo);

        currentGameWorld = gameWorldOne;
        currentGui = guiOne;
        currentController = playerOneController;
    }

    public void switchPlayer(){
        currentGameWorld.getTime().setPaused(true);

        if (isPlayerOneTurn){
            currentGameWorld = gameWorldTwo;
            currentGui = guiTwo;
            currentController = playerTwoController;
        }
        else{
            currentGameWorld = gameWorldOne;
            currentGui = guiOne;
            currentController = playerOneController;
        }

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(currentGui.getGuiStage());
        multiplexer.addProcessor(currentController);
        currentGameWorld.getTime().setPaused(false);
        Gdx.input.setInputProcessor(multiplexer);

        currentGameWorld.getPlayer().getStamina().reset();

        isPlayerOneTurn = !isPlayerOneTurn;
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(currentGui.getGuiStage());
        multiplexer.addProcessor(currentController);
        Gdx.input.setInputProcessor(multiplexer);

        currentGameWorld.getTime().setPaused(false);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (game.pauseScreen == null) {
                game.pauseScreen = new PauseScreen(game);
            }
            game.setScreen(game.pauseScreen);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            currentGameWorld.setGameClear(true);
        }

        if (currentGameWorld.getPlayer().getStamina().getCurrentStamina() == 0){
            game.twoPlayerGameScreen.switchPlayer();
        }

        if (isPlayerOneTurn) {
            gameWorldOne.render(delta);
            guiOne.render(delta);
        }
        else{
            gameWorldTwo.render(delta);
            guiTwo.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        gameWorldOne.getGameWorldStage().getViewport().update(width, height);
        guiOne.getGuiStage().getViewport().update(width,height);

        gameWorldTwo.getGameWorldStage().getViewport().update(width, height);
        guiTwo.getGuiStage().getViewport().update(width,height);
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
        gameWorldOne.dispose();
        guiOne.dispose();

        gameWorldTwo.dispose();
        guiTwo.dispose();
    }
}
