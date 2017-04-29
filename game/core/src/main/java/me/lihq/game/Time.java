package me.lihq.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * EXTENDED, no longer a singleton
 * Actor that keeps track of game play totalTime. It should pause while in pause screen.
 */

public class Time extends Actor {
    private float totalTime;
    private boolean isPaused = false;

    public Time() {
        this.totalTime = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!isPaused){
            this.totalTime += delta;
        }
    }

    public float getTotalTime(){
        return this.totalTime;
    }

    public void setPaused(boolean pause){
        this.isPaused = pause;
    }

    public void reset(){
        totalTime = 0;
        isPaused = false;
    }
}
