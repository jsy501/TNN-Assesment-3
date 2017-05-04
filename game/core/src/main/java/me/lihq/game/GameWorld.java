package me.lihq.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;

import me.lihq.game.gui.Gui;
import me.lihq.game.gui.speechbubbles.InteractionSelectionBubble;
import me.lihq.game.models.Clue;
import me.lihq.game.models.Door;
import me.lihq.game.models.Room;
import me.lihq.game.models.Vector2Int;
import me.lihq.game.people.Direction;
import me.lihq.game.people.Npc;
import me.lihq.game.people.PersonState;
import me.lihq.game.people.Player;
import me.lihq.game.models.RoomArrow;
import me.lihq.game.screen.GameClearScreen;

/**
 * EXTENDED
 *
 * A working executable for the game can be found on the website http://nxn173.wixsite.com/teamquestionmark/about
 * under assessment 4. However the website links to google drive which is actually holding the executable jar so here
 * is a direct link if you would prefer not to go through the website: https://drive.google.com/file/d/0B7zqZWea_dRJWTlrel9kdGdScWM/view?usp=sharing
 *
 * Container for all of the core game objects in the game
 */
public class GameWorld {
    public GameMain game;
    private Gui gui;

    /**
     * ADDED FIELDS
     */
    private Time time;
    private Score score;

    public RoomManager roomManager;
    public NpcManager npcManager;
    public ClueManager clueManager;

    private Player player;
    private Stage gameWorldStage;

    //group that has all the character actors in a room including player
    private Group characterGroup;

    //group that has all the clue glints
    private Group clueGroup;

    //group that has all the room arrow
    private Group roomArrowGroup;

    private CustomTiledMapRenderer tiledMapRenderer;

    private CameraManager cameraManager;

    private Interaction interaction;
    private ConversationManager conversationManager;

    //when the game clear condition is met, the value is changed to true
    private boolean isGameClear = false;

    public GameWorld(GameMain game, Player selectedPlayer){
        this.game = game;
        time = new Time();
        score = new Score();

        SpriteBatch gameWorldBatch = new SpriteBatch();
        gameWorldStage = new Stage(new FitViewport(GameMain.GAME_WIDTH / Settings.ZOOM,
                GameMain.GAME_HEIGHT / Settings.ZOOM), gameWorldBatch);

        roomManager = new RoomManager(game.assetLoader);
        npcManager = new NpcManager(roomManager, game.assetLoader);
        clueManager = new ClueManager(npcManager, roomManager, game.assetLoader);

        player = selectedPlayer;
        player.setCurrentRoom(roomManager.getRoom(0));
        Vector2Int randomLocation = player.getCurrentRoom().getRandomLocation();
        player.setTilePosition(randomLocation.x, randomLocation.y);
        player.setGameWorld(this);

        cameraManager = new CameraManager((OrthographicCamera) gameWorldStage.getCamera(), player);

        characterGroup = new Group();
        characterGroup.setName("characterGroup");
        clueGroup = new Group();
        roomArrowGroup = new Group();

        tiledMapRenderer = new CustomTiledMapRenderer(player.getCurrentRoom(), gameWorldBatch);

        //room arrow group is added first so it draws behind player
        gameWorldStage.addActor(roomArrowGroup);

        characterGroup.addActor(player);
        for (Npc npc : player.getCurrentRoom().getNpcArray()) {
            characterGroup.addActor(npc);
        }

        for (Clue clue : player.getCurrentRoom().getClueArray()){
            clueGroup.addActor(clue);
        }

        for (RoomArrow arrow : player.getCurrentRoom().getRoomArrowArray()){
            roomArrowGroup.addActor(arrow);
        }

        gameWorldStage.addActor(clueGroup);
        gameWorldStage.addActor(characterGroup);

        interaction = new Interaction(this);
        conversationManager = new ConversationManager(game.assetLoader);
    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * Change room to render and the characters that in the room.
     * @param roomId id of the room that needs to be changed to.
     */
    public void changeRoom(int roomId) {
        Room entryRoom = roomManager.getRoom(roomId);
        if (entryRoom.isLocked()){
            return;
        }

        game.assetLoader.door.play();
        //prevents colliding with doors multiple times during transition
        player.setCanMove(false);

        //actual room transition happens after fade out
        game.fadeInOut.addAction(Actions.sequence(
                Actions.fadeIn(0.5f),
                Actions.run(() -> {
                    Room exitRoom = player.getCurrentRoom();

                    Vector2 entryPosition = new Vector2();
                    Direction entryDirection = player.getDirection();

                    //get the direction of player should be facing and the position to be when entering the room
                    for (Door door : entryRoom.getEntryArray()) {
                        if (door.getConnectedRoomId() == exitRoom.getID()) {
                            entryPosition.set(door.getX() + door.getWidth() / 2, door.getY() + door.getHeight() / 2);
                            entryDirection = door.getDirection();
                            break;
                        }
                    }

                    // clear all the actors in the previous room
                    characterGroup.clear();
                    clueGroup.clear();
                    roomArrowGroup.clear();

                    characterGroup.addActor(player);

                    player.setCurrentRoom(entryRoom);
                    player.setDirection(entryDirection);
                    player.setPosition(entryPosition.x, entryPosition.y);

                    for (Npc npc : player.getCurrentRoom().getNpcArray()) {
                        characterGroup.addActor(npc);
                    }
                    for (Clue clue : player.getCurrentRoom().getClueArray()) {
                        clueGroup.addActor(clue);
                    }
                    for (RoomArrow arrow : player.getCurrentRoom().getRoomArrowArray()) {
                        roomArrowGroup.addActor(arrow);
                    }

                    gui.setRoomTag(entryRoom);

                    //prevents camera lerp when moving between rooms
                    cameraManager.getCamera().position.x = player.getDefaultCameraFocusX();
                    cameraManager.getCamera().position.y = player.getDefaultCameraFocusY();

                    //set the new room for the tile map renderer
                    tiledMapRenderer.setRenderingRoom(player.getCurrentRoom());
                    tiledMapRenderer.setView((OrthographicCamera) gameWorldStage.getCamera());

                    player.setCanMove(true);
                }),
                Actions.fadeOut(0.5f)));
    }

    /**
     * Start an interaction with an npc. Introduction dialogues are loaded into conversation manager and
     * the camera is set to interaction mode.
     * @param interactingNpc npc to interact with
     */
    public void startInteraction(Npc interactingNpc){
        interaction.setInteractingNpc(interactingNpc);

        player.setCanMove(false);
        interactingNpc.setCanMove(false);
        interactingNpc.setDirection(player.getDirection().getOpposite());
        conversationManager.addSpeechBubble(player, player.getDialogue().getIntroduction());
        conversationManager.addSpeechBubble(interactingNpc, interactingNpc.getDialogue().getIntroduction());
        conversationManager.addSpeechBubble(new InteractionSelectionBubble(player, game.assetLoader, conversationManager, gui));
        conversationManager.startConversation(gui.getGuiStage());

        cameraManager.startInteractionMode(interactingNpc);
    }

    public void haltInteraction(){
        interaction.getInteractingNpc().setCanMove(true);
        player.setCanMove(true);

        conversationManager.clear();

        cameraManager.haltInteractionMode();
    }

    /*
    EXTENDED CODE START
     */

    /**
     * procedure when the player had successfully solved the puzzle for secret room access
     */
    public void puzzleSuccess(){
        gui.displayInfo("A secret door opened!");
        roomManager.getSecretRoom().setLocked(false);
        clueManager.getSecretDoorClue().setVisible(false);
    }

    public void puzzleFail(){
        gui.displayInfo("Puzzle failed! Try again!");
    }

    /*
    EXTENDED CODE END
     */

    public void render(float delta){
        if (conversationManager.isFinished()){
            conversationManager.setFinished(false);
            haltInteraction();
        }

        cameraManager.update();

        tiledMapRenderer.setRenderingRoom(player.getCurrentRoom());
        tiledMapRenderer.setView(cameraManager.getCamera());

        tiledMapRenderer.render();

        gameWorldStage.act();

        //sort characters by their y coordinate so that actors with lesser y coordinate get drawn first
        characterGroup.getChildren().sort((actor1, actor2) -> (int) (actor2.getY() - actor1.getY()));
        gameWorldStage.draw();

        //layers that need to be rendered after character
        tiledMapRenderer.renderLastLayer();
    }

    public void setGameClear(boolean gameClear) {
        isGameClear = gameClear;
    }

    public boolean isGameClear() {
        return isGameClear;
    }

    public Player getPlayer() {
        return player;
    }

    public Stage getGameWorldStage() {
        return gameWorldStage;
    }

    public Gui getGui() {
        return gui;
    }

    public Time getTime(){
        return time;
    }

    public Score getScore(){
        return score;
    }

    public Interaction getInteraction(){
        return interaction;
    }

    public ConversationManager getConversationManager(){
        return conversationManager;
    }

    public void dispose(){
        tiledMapRenderer.dispose();
        gameWorldStage.dispose();
    }
}
