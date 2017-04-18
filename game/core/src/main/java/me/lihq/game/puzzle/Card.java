package me.lihq.game.puzzle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.lihq.game.AssetLoader;

/**
 * This is the card class for the puzzle game.
 * Each card will have its only uid in int.
 * the isFlip a boolean to show the state of the card(where to indicate which image should it show)
 */
public class Card extends Image {
    private int uid;
    private boolean isFlip = false;
    private TextureRegionDrawable faceUpImage;
    private TextureRegionDrawable faceDownImage;

    public Card(int uid, AssetLoader assetLoader) {
        super(assetLoader.cardTextureArray.peek());
        this.uid = uid;

        faceUpImage = new TextureRegionDrawable(new TextureRegion(assetLoader.cardTextureArray.get(uid-1)));
        faceDownImage = (TextureRegionDrawable) getDrawable();
    }

    /**
     * isMatch method test if the clicked card matches with previous card.
     */
    public boolean isMatch(Card cardNext) {
        return (this.getUid() == cardNext.getUid());
    }

    /**
     * return the uid of the card.
     */
    public int getUid() {
        return uid;
    }

    /**
     * Method allows to change the Flip state.
     */
    public void setFlip(Boolean flip) {
        isFlip = flip;
        System.out.println(isFlip + ", " + uid);
        if (flip) setDrawable(faceUpImage);
        else setDrawable(faceDownImage);
    }

    /**
     * Method to get the flip state.
     */
    public boolean isFlip() {
        return isFlip;
    }
}