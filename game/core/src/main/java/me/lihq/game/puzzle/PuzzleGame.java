package me.lihq.game.puzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PuzzleGame extends Game {
    public static Game instance;
    SpriteBatch batch;
    Texture img;

    @Override
    public void create () {
        instance = this;
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        setScreen(new PuzzleStartScreen());
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        // getScreen().render(Gdx.graphics.getDeltaTime());

        super.render();

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}