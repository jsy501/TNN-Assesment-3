package me.lihq.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import me.lihq.game.Puzzle;

public class PuzzleScreen implements Screen {
    private Puzzle stage;

    public void setCallback(Runnable callback) {

        this.callback = callback;
        //TODO interface for success and fail
    }

    private Runnable callback;

    public Runnable getCallback() {
        return callback;
    }

    @Override
    public void show() {
        this.stage = new Puzzle(this);
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
