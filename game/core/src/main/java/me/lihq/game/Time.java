package me.lihq.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * NEW
 * Actor that keeps track of game play totalTime. It should pause while in pause screen.
 */

public class Time extends Actor {
    private float totalTime;
    private boolean paused = false;

    public Time() {
        this.totalTime = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!paused){
            this.totalTime += delta;
        }
    }

    public float getTotalTime(){
        return this.totalTime;
    }

    public void setPaused(boolean pause){
        this.paused = pause;
    }

    public void reset(){
        totalTime = 0;
        paused = false;
    }
}
