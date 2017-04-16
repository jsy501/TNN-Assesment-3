package me.lihq.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import me.lihq.game.StartMenu;

public class PuzzleStartScreen implements Screen {
    private StartMenu stage;


    @Override
    public void show() {
        this.stage = new StartMenu(this);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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


