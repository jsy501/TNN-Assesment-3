package me.lihq.game.screen.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;

import me.lihq.game.GameMain;

/**
 * Abstract class for menu template for the screens.
 */

public abstract class MenuTable extends Table {
    protected Skin menuSkin;

    protected Table titleTable;
    protected Table contentTable;
    protected Table buttonTable;

    /**
     * Constructor for the menu
     *
     * @param skin - The skin for the menu
     */
    public MenuTable(Skin skin, String title) {
        menuSkin = skin;

        align(Align.top);
        setFillParent(true);

        titleTable = new Table();
        contentTable = new Table();
        buttonTable = new Table();

        Label titleLabel = new Label(title, menuSkin, "title", Color.RED);
        titleTable.add(titleLabel).expandX().fillX();

        add(titleTable).padTop(100).padBottom(70).row();
        add(contentTable).row();
        add(buttonTable).expand(true, true);
    }


    public void addButton(Button button){
        buttonTable.add(button).padBottom(Value.percentHeight(0.5f,button));
        buttonTable.row();
    }
}
