package me.lihq.game.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.lihq.game.GameMain;
import me.lihq.game.GameWorld;
import me.lihq.game.gui.windows.*;
import me.lihq.game.models.Room;
import me.lihq.game.Score;

/**
 * EXTENDED
 * A container for all of the gui element of the game
 */

public class Gui {
    private GameWorld gameWorld;

    private RoomTag roomTag;
    private StaminaBar staminaBar;
    private StatusBar statusBar;

    private SpriteBatch guiBatch;
    private Stage guiStage;

    // all of the gui windows in the game
    private InfoWindow infoWindow;
    private PromptWindow promptWindow;
    private InventoryWindow inventoryWindow;
    private PersonalityMeterWindow personalityMeterWindow;
    private NpcNoteWindow npcNoteWindow;
    private ClueSelectionWindow clueSelectionWindow;
    private AccuseWindow accuseWindow;

    public Gui(GameMain game, GameWorld gameWorld, boolean isTwoPlayerMode){
        this.gameWorld = gameWorld;

        guiBatch = new SpriteBatch();
        guiStage = new Stage(new FitViewport(GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT), guiBatch);

        //table for main game screen gui
        Table table = new Table();
        table.setFillParent(true);

        //table for upper screen ui
        Table upperTable = new Table();
        upperTable.align(Align.topRight);

        if (isTwoPlayerMode) {
            staminaBar = new StaminaBar(game, gameWorld);
            upperTable.add(staminaBar);
        }

        //table for lower screen ui
        Table lowerTable = new Table();
        lowerTable.align(Align.bottom);

        roomTag = new RoomTag(game.assetLoader.roomTagBorder, game.assetLoader.roomTagFont);
        table.addActor(roomTag);

        statusBar = new StatusBar(game, this);
        lowerTable.add(statusBar);

        table.add(upperTable).height(Value.percentHeight(0.5f, table)).fillX().row();
        table.add(lowerTable).height(Value.percentHeight(0.5f, table)).fillX();

        guiStage.addActor(table);


        // time and score must be added into gui stage for them to be updated
        guiStage.addActor(gameWorld.getTime());
        guiStage.addActor(gameWorld.getScore());


        //instantiate all of the gui windows altogether
        infoWindow = new InfoWindow(game.assetLoader, this, gameWorld);
        //pressing space will also close the info window
        infoWindow.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE){
                    infoWindow.hide();
                }
                return true;
            }
        });

        promptWindow = new PromptWindow(game.assetLoader, this, gameWorld);
        inventoryWindow = new InventoryWindow(game.assetLoader, this, gameWorld);
        personalityMeterWindow = new PersonalityMeterWindow(game.assetLoader, this, gameWorld);
        npcNoteWindow = new NpcNoteWindow(game.assetLoader, this, gameWorld);
        clueSelectionWindow = new ClueSelectionWindow(game.assetLoader, this, gameWorld);
        accuseWindow = new AccuseWindow(game.assetLoader, this, gameWorld);
    }

    /**
     * Set the room tag to be appear from top left corner of the screen
     * @param room room to be used for room tag
     */
    public void setRoomTag(Room room){
        roomTag.setRoomName(room.getName());
        roomTag.addAction(Actions.sequence(
                Actions.parallel(
                    Actions.fadeIn(1f),
                    Actions.moveBy(0, -roomTag.getHeight() - 10, 1f)),
                Actions.delay(2f),
                Actions.fadeOut(1f)));
    }

    /**
     * Show a pop up window that displays an actor and a string below.
     * @param actor Actor to show.
     * @param info String to be added below.
     */
    public void displayInfo(Actor actor, String info){
        infoWindow.getContentTable().clearChildren();
        infoWindow.getContentTable().add(actor).row();
        Label infoLabel = new Label(info, infoWindow.getSkin(), "dialog");
        infoLabel.setWrap(true);
        infoLabel.setAlignment(Align.center);
        infoWindow.getContentTable().add(infoLabel).width(500);
        infoWindow.show(guiStage);
    }

    /**
     * Show a pop up window that displays a string.
     * @param info String to be displayed.
     */
    public void displayInfo(String info){
        infoWindow.getContentTable().clearChildren();
        Label infoLabel = new Label(info, infoWindow.getSkin(), "dialog");
        infoLabel.setWrap(true);
        infoLabel.setAlignment(Align.center);
        infoWindow.getContentTable().add(infoLabel).width(500);
        infoWindow.show(guiStage);
    }

    /*
    EXTENDED CODE START
     */

    /**
     * Show a pop up prompt window for mini puzzle opening secret door
     */
    public void displayPrompt(String text){
        promptWindow.getContentTable().clearChildren();
        Label infoLabel = new Label(text, infoWindow.getSkin(), "dialog");
        infoLabel.setWrap(true);
        infoLabel.setAlignment(Align.center);
        promptWindow.getContentTable().add(infoLabel).width(500);
        promptWindow.show(guiStage);
    }

    /*
    EXTENDED CODE END
     */

    public void render(float delta){
        guiStage.act(delta);
        guiStage.draw();
    }

    public Stage getGuiStage(){
        return guiStage;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public InventoryWindow getInventoryWindow() {
        return inventoryWindow;
    }

    public PersonalityMeterWindow getPersonalityMeterWindow() {
        return personalityMeterWindow;
    }

    public NpcNoteWindow getNpcNoteWindow() {
        return npcNoteWindow;
    }

    public ClueSelectionWindow getClueSelectionWindow() {
        return clueSelectionWindow;
    }

    public AccuseWindow getAccuseWindow() {
        return accuseWindow;
    }

    public void dispose(){
        guiStage.dispose();
    }
}
