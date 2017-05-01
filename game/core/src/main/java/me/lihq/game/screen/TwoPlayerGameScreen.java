package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.FadeInOut;
import me.lihq.game.gui.Gui;
import me.lihq.game.people.Player;
import me.lihq.game.people.controller.PlayerController;

/**
 * NEW
 * Game screen for two player mode
 */

public class TwoPlayerGameScreen extends GameScreen{
    private PlayerController playerOneController;
    private PlayerController playerTwoController;

    private GameWorld gameWorldOne;
    private GameWorld gameWorldTwo;

    private Gui guiOne;
    private Gui guiTwo;

    private boolean isPlayerOneTurn = true;

    public TwoPlayerGameScreen(GameMain game, Player playerOne, Player playerTwo) {
        super(game);

        playerOne.getStamina().enable();
        gameWorldOne = new GameWorld(game, playerOne);

        playerTwo.getStamina().enable();
        gameWorldTwo = new GameWorld(game, playerTwo);

        guiOne = new Gui(game, gameWorldOne, true);
        guiTwo = new Gui(game, gameWorldTwo, true);

        gameWorldOne.setGui(guiOne);
        gameWorldTwo.setGui(guiTwo);

        playerOneController = new PlayerController(gameWorldOne);
        playerTwoController = new PlayerController(gameWorldTwo);

        currentGameWorld = gameWorldOne;
        currentGui = guiOne;
        currentController = playerOneController;
    }

    private void switchPlayer(){
        currentGameWorld.getPlayer().getStamina().reset();
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

        isPlayerOneTurn = !isPlayerOneTurn;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (currentGameWorld.getPlayer().getStamina().isDepleted()){
            currentGameWorld.getPlayer().getStamina().setDepleted(false);
            currentGameWorld.getPlayer().setCanMove(false);

            game.fadeInOut.addAction(Actions.sequence(
                    Actions.fadeIn(0.5f),
                    Actions.run(this::switchPlayer),
                    Actions.fadeOut(0.5f),
                    Actions.run(() -> currentGameWorld.getPlayer().setCanMove(true))
            ));
        }
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
