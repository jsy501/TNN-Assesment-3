package me.lihq.game.puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PuzzleEndScreen implements Screen {
    String message;
    private SpriteBatch batch = new SpriteBatch();
    BitmapFont font;

    public PuzzleEndScreen(String message) {
        this.message = message;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("calibri.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = 24;
        font = generator.generateFont(p);
        generator.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, message,100,100);
        batch.end();



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

