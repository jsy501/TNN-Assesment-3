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

/**
 * NEW
 * Screen that users pick their detectives.
 */

public class SinglePlayerSelectionScreen extends AbstractScreen {
    private Array<Player> playerArray;

    private Stage stage;

    private Table table;
    private Label selectionLabel;
    private Table selectionTable;
    private Dialog selectionConfirmWindow;

    private Player selectedPlayer;

    public SinglePlayerSelectionScreen(GameMain game) {
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

        selectionLabel = new Label("Choose your detective!", assetLoader.uiSkin, "title");

        selectionTable = new Table();

        selectionConfirmWindow = new Dialog("", game.assetLoader.uiSkin){
            @Override
            protected void result(Object object) {
                if (object.equals(true)){
                    game.singlePlayerGameScreen = new SinglePlayerGameScreen(game, selectedPlayer);
                    game.setScreen(game.singlePlayerGameScreen);
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
                    selectedPlayer = player;

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
