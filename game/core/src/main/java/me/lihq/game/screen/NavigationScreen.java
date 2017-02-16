package me.lihq.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import me.lihq.game.*;
import me.lihq.game.models.Clue;
import me.lihq.game.models.Door;
import me.lihq.game.models.Room;
import me.lihq.game.people.Direction;
import me.lihq.game.people.NPC;
import me.lihq.game.people.PersonState;
import me.lihq.game.people.controller.GamePadController;
import me.lihq.game.people.controller.PlayerController;
import me.lihq.game.screen.elements.FadeInOut;
import me.lihq.game.Gui;
import me.lihq.game.screen.elements.RoomArrow;

/**
 * This is the screen that is responsible for the navigation of the player around the game.
 * It displays the current room that the player is in, and allows the user to move the player around between rooms.
 */
public class NavigationScreen extends AbstractScreen
{
    /**
     * The controller that listens for key inputs
     */
    public PlayerController playerController;
    public GamePadController gamePadController;

    /**
     * The black actor that is used to fade in/out
     */
    private FadeInOut fadeInOut;

    private GameWorld gameWorld;
    private Gui gui;

    /**
     * Initialises the navigation screen
     *
     * @param game - The main game instance
     */


    public NavigationScreen(GameMain game)
    {
        super(game);

        gameWorld = new GameWorld(game);

        gui = new Gui(game);

        gamePadController = new GamePadController(player);

        playerController = new PlayerController(player);
    }

    /**
     * This is ran when the navigation screen becomes the visible screen in GameMain
     */
    @Override
    public void show()
    {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(guiStage);
        multiplexer.addProcessor(playerController);
        Gdx.input.setInputProcessor(multiplexer);

        Controllers.addListener(gamePadController);

    }

    public void changeRoom(int roomId){
        player.setState(PersonState.STANDING);
        Room exitRoom = player.getCurrentRoom();
        Room entryRoom = game.roomManager.getRoom(roomId);
        Vector2 entryPosition = new Vector2();
        Direction entryDirection = player.getDirection();

        for (Door door : entryRoom.getEntryArray()){
            if (door.getConnectedRoomId() == exitRoom.getID()){
                entryPosition.set(door.getX() + door.getWidth()/2, door.getY() + door.getHeight()/2);
                entryDirection = door.getDirection();
                break;
            }
        }

        Direction finalEntryDirection = entryDirection;

        fadeInOut.addAction(Actions.sequence(Actions.fadeIn(0.5f),Actions.run(new Runnable() {
            @Override
            public void run() {
                characterGroup.clear();
                clueGroup.clear();
                roomArrowGroup.clear();

                characterGroup.addActor(player);

                player.setCurrentRoom(entryRoom);
                player.setDirection(finalEntryDirection);
                player.setPosition(entryPosition.x, entryPosition.y);

                for (NPC npc : player.getCurrentRoom().getNpcArray()) {
                    characterGroup.addActor(npc);
                }
                for (Clue clue : player.getCurrentRoom().getClueArray()){
                    clueGroup.addActor(clue);
                }
                for (RoomArrow arrow : player.getCurrentRoom().getRoomArrowArray()){
                    roomArrowGroup.addActor(arrow);
                }

                gui.setRoomTag(entryRoom);

                player.setCanMove(true);
            }
        }), Actions.fadeOut(0.5f)));
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta)
    {
        gameWorldStage.getCamera().position.x = player.getX();
        gameWorldStage.getCamera().position.y = player.getY();
        gameWorldStage.getCamera().update();

        tiledMapRenderer.setRenderingRoom(player.getCurrentRoom());
        tiledMapRenderer.setView((OrthographicCamera) gameWorldStage.getCamera());

        tiledMapRenderer.render();

        gameWorldStage.act();

        //sort characters by their y coordinate so that actors with lesser y coordinate get drawn first
        characterGroup.getChildren().sort((actor1, actor2) -> (int) (actor2.getY() - actor1.getY()));
        gameWorldStage.draw();

        tiledMapRenderer.renderLastLayer();


        guiStage.act();
        guiStage.draw();
    }

    /**
     * This is called when the window is resized
     *
     * @param width  - The new width
     * @param height - The new height
     */
    @Override
    public void resize(int width, int height)
    {
        gameWorldStage.getViewport().update(width, height);
        guiStage.getViewport().update(width,height);
    }

    /**
     * This is called when the focus is lost on the window
     */
    @Override
    public void pause()
    {

    }

    /**
     * This method is called when the window is brought back into focus
     */
    @Override
    public void resume()
    {

    }

    /**
     * This method is called when the user hides the window
     */
    @Override
    public void hide()
    {

    }

    /**
     * This is to be called when you want to dispose of all data
     */
    @Override
    public void dispose()
    {
        tiledMapRenderer.dispose();
        gameWorldStage.dispose();
        guiStage.dispose();
    }
}