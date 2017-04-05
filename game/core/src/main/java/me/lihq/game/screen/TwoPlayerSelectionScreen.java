package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.AssetLoader;
import me.lihq.game.GameMain;
import me.lihq.game.gui.Slot;
import me.lihq.game.people.Player;

public class TwoPlayerSelectionScreen extends AbstractScreen{
    private Array<Player> playerArray;

    private Stage stage;

    private Table table;
    private Label selectionLabel;
    private Table selectionTable;
    private Dialog selectionConfirmWindow;

    private Slot selectedPlayerSlot;
    private Player playerOne;
    private Player playerTwo;

    public TwoPlayerSelectionScreen(GameMain game) {
        super(game);

        AssetLoader assetLoader = game.assetLoader;

        Json json = new Json();
        playerArray = new Array<>();

        Array<JsonValue> playerJsonData = json.readValue(Array.class, assetLoader.playerJsonData);
        for (JsonValue data : playerJsonData) {
            playerArray.add(new Player(data, assetLoader.playerSpriteSheetArray.get(data.getInt("id"))));
        }

        stage = new Stage(new FitViewport(GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT));

        table = new Table();
        table.setFillParent(true);

        selectionLabel = new Label("Choose detective for Player 1!", assetLoader.uiSkin, "title");

        selectionTable = new Table();

        selectionConfirmWindow = new Dialog("", game.assetLoader.uiSkin){
            @Override
            protected void result(Object object) {
                if (object.equals(true)){
                    if (playerOne == null){
                        playerOne = (Player) selectedPlayerSlot.getSlotActor();
                        selectionLabel.setText("Choose detective for Player 2!");
                        selectedPlayerSlot.remove();
                    }
                    else{
                        playerTwo = (Player) selectedPlayerSlot.getSlotActor();
                        game.twoPlayerGameScreen = new TwoPlayerGameScreen(game, playerOne, playerTwo);
                        game.setScreen(game.twoPlayerGameScreen);
                    }
                }
                else {
                    hide();
                }
            }
        };

        selectionConfirmWindow.button("OK", true);
        selectionConfirmWindow.button("Cancel", false);
    }

    @Override
    public void show() {
        for (Player player : playerArray) {
            Slot playerSlot = new Slot(player, game.assetLoader.uiSkin);
            playerSlot.addListener(new InputListener(){
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    playerSlot.setCursorOver(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    playerSlot.setCursorOver(false);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    selectedPlayerSlot = playerSlot;

                    selectionConfirmWindow.getContentTable().clear();
                    selectionConfirmWindow.text(player.getDescription());
                    selectionConfirmWindow.show(stage);
                }
            });

            selectionTable.add(playerSlot);
        }

        table.add(selectionLabel).row();
        table.add(selectionTable);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
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
