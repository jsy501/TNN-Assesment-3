package me.lihq.game.screen.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Scaling;
import me.lihq.game.GameMain;

/**
 * EXTENDED
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

        Label titleLabel = new Label(title, menuSkin, "menu-title");
        titleTable.add(titleLabel).expandX().fillX();

        add(titleTable).padTop(100).expand().row();
        add(contentTable).padBottom(100).row();
        add(buttonTable).padBottom(100);
    }

    /*
    EXTENDED CODE START
     */

    /**
     * constructor for title screen that displays an image
     * @param skin skin to be used for menu
     * @param texture image to be displayed
     */
    public MenuTable(Skin skin, Texture texture) {
        menuSkin = skin;

        align(Align.top);
        setFillParent(true);

        titleTable = new Table();
        contentTable = new Table();
        buttonTable = new Table();

        Image titleTexture = new Image(texture);
        titleTexture.setScaling(Scaling.fillX);
        titleTable.add(titleTexture).width(Value.percentWidth(0.6f, this));

        add(titleTable).padTop(10).row();
        add(contentTable).row();
        add(buttonTable).expand(true, true);
    }

    /*
    EXTENDED CODE END
     */


    public void addButton(Button button){
        buttonTable.add(button).padBottom(Value.percentHeight(0.5f,button));
        buttonTable.row();
    }
}
