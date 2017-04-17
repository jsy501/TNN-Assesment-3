package me.lihq.game.puzzle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This is the card class for the puzzle game.
 * Each card will have its only uid in int.
 * The front string is to record the file address of the front image
 * The BACKIMAGE string is fixed file address of the back image
 * the isFlip a boolean to show the state of the card(where to indicate which image should it show)
 * The card class have two parameter(uid and front string) to initialise.
 */
public class Card extends ImageButton {
    private int uid;
    private String front;
    private static Texture BACKIMAGE = new Texture("puzzle/qm.png");
    private boolean isFilp = false;

    public Card() {
        super(new TextureRegionDrawable(new TextureRegion(BACKIMAGE)));
    }

    public Card(int uid, String front) {
        super(new TextureRegionDrawable(new TextureRegion(BACKIMAGE)));
        this.uid = uid;
        this.front = front;
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
    public void setIsFilp(Boolean flip) {
        isFilp = flip;
        System.out.println(isFilp + ", " + uid);
        getStyle().imageUp = (new TextureRegionDrawable(new TextureRegion(flip ? new Texture(front) : BACKIMAGE)));

    }

    /**
     * Method to get the flip state.
     */
    public boolean getIsFilp() {
        return isFilp;
    }
}