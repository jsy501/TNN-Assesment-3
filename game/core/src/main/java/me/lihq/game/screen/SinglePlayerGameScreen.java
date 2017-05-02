package me.lihq.game.screen;

import me.lihq.game.*;
import me.lihq.game.people.Player;
import me.lihq.game.people.controller.PlayerController;
import me.lihq.game.gui.Gui;

/**
 * NEW
 * This is the screen that is responsible for single player version of the game.
 * It displays the current room that the player is in, and allows the user to move the player around between rooms.
 */
public class SinglePlayerGameScreen extends GameScreen
{
    private GameWorld gameWorld;
    private Gui gui;

    /**
     * Initialises the screen
     *
     * @param game - The main game instance
     * @param selectedPlayer The detective that the player selected
     */


    public SinglePlayerGameScreen(GameMain game, Player selectedPlayer)
    {
        super(game);

        gameWorld = new GameWorld(game, selectedPlayer);

        gui = new Gui(game, gameWorld, false);

        gameWorld.setGui(gui);

        currentGameWorld = gameWorld;
        currentGui = gui;
        currentController = new PlayerController(gameWorld);
    }

    @Override
    public void pause() {

    }

    /**
     * This is to be called when you want to dispose of all data
     */
    @Override
    public void dispose()
    {
        gameWorld.dispose();
        gui.dispose();
    }
}
