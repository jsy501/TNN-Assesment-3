package me.lihq.game.people;


import me.lihq.game.Settings;

/**
 * NEW
 * The class for managing stamina system in two player mode. Disabled by default.
 */

public class Stamina {
    private final float DEFAULT_MAX = 100f;
    private final float ACTION_COST = 20f;
    private final float MOVE_COST = 1f;    //cost per tile move

    private float costFactor = 1; // the factor of cost to be spent
    private float currentStamina;
    private boolean isEnabled = false;
    private boolean isDepleted = false;

    public Stamina(){
        currentStamina = DEFAULT_MAX;
    }

    /**
     * Spend stamina depending on the distance the player moved
     * @param distanceX distance moved in x direction
     * @param distanceY distance moved in y direction
     */

    public void move(float distanceX, float distanceY){
        if (isEnabled) {
            currentStamina -= Math.abs(distanceX) * (MOVE_COST / Settings.TILE_SIZE) * costFactor;
            currentStamina -= Math.abs(distanceY) * (MOVE_COST / Settings.TILE_SIZE) * costFactor;
            if (currentStamina < 0) {
                isDepleted = true;
            }
        }
    }

    /**
     * Use stamina to carry out an action
     * @return true if there is enough stamina, false otherwise
     */

    public boolean action(){
        if (!isEnabled){
            return true;
        }

        if (currentStamina > ACTION_COST) {
            currentStamina -= ACTION_COST * costFactor;
            return true;
        }

        else{
            return false;
        }
    }

    public void enable(){
        isEnabled = true;
    }

    public boolean isDepleted() {
        return isDepleted;
    }

    public void setDepleted(boolean depleted) {
        isDepleted = depleted;
    }

    public void reset(){
        currentStamina = DEFAULT_MAX;
    }

    public float getCurrentStamina(){
        return currentStamina;
    }

    public void setCostFactor(float newCostFactor){
        costFactor = newCostFactor;
    }

    /**
     * Code below is for tests only.
     */

    public void setCurrentStamina(float newStamina){currentStamina = newStamina;}

    public boolean checkEnabled(){return isEnabled;}

}