package me.lihq.game.living;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import me.lihq.game.Assets;
import me.lihq.game.Settings;
import me.lihq.game.models.Vector2Int;

/**
 * The abstract person is an abstract representation of a person. A person can be a non playable character or Player.
 * It extends the sprite class which provides methods for the person to be rendered in the game.
 */
public abstract class AbstractPerson extends Sprite
{
    /**
     * This is the location of the person in the room in terms of tiles eg (0,0) would be the bottom left of the room
     */
    protected Vector2Int tileCoordinates = new Vector2Int(0, 0);

    /**
     * This is a temporary store of the pixel coordinate of the person. The persons actual position is set to this by the rendering thread.
     * This is to avoid jolting.
     * <p>
     * Avoid using Sprites setPosition as if it is changed mid render it will cause jolting
     */
    protected Vector2 tempCoordinates = new Vector2().set(0.0f, 0.0f);

    protected Vector2Int startPosition = new Vector2Int(0, 0);
    protected Vector2Int destinationPosition = new Vector2Int(0, 0);

    protected float animTimer;
    protected float ANIM_TIME = Settings.TPS / 3.5f;

    protected Texture spriteSheet;
    protected TextureRegion currentRegion;

    /**
     * The direction determines the way the character is facing.
     */
    protected Direction direction = Direction.EAST;

    protected PersonState state;

    /**
     * This constructs the player calling super on the sprite class
     *
     * @param img this a path to the image
     */
    public AbstractPerson(String img)
    {
        super(new TextureRegion(Assets.loadTexture(img), 0, 0, 32, 37));

        this.spriteSheet = Assets.loadTexture(img);
        this.currentRegion = new TextureRegion(Assets.loadTexture(img), 0, 0, 32, 37);

        this.setPosition(tileCoordinates.getX() * Settings.TILE_SIZE, tileCoordinates.getY() * Settings.TILE_SIZE);
        this.state = PersonState.STANDING;
    }

    /**
     * This method moves the coordinates in the AbstractPersons tempCoordinates to
     * the Sprites position so that it can then be rendered at the correct location.
     */
    public void pushCoordinatesToSprite()
    {
        setPosition(tempCoordinates.x, tempCoordinates.y);
    }


    /**
     * This sets the tile coordinates of the person.
     *
     * @param x The x coordinate of the tile grid.
     * @param y The y coordinate of the tile grid.
     */
    public void setTileCoordinates(int x, int y)
    {
        tileCoordinates.x = x;
        tileCoordinates.y = y;

        setTempCoords(x * Settings.TILE_SIZE, y * Settings.TILE_SIZE);
    }

    /**
     * This is called to update the players position.
     * Called from the game loop, it interpolates the movement so that the person moves smoothly from tile to tile.
     */
    public void updateMotion()
    {
        if (this.state == PersonState.WALKING) {
            this.tempCoordinates.x = Interpolation.linear.apply(startPosition.x, destinationPosition.x, animTimer / ANIM_TIME);
            this.tempCoordinates.y = Interpolation.linear.apply(startPosition.y, destinationPosition.y, animTimer / ANIM_TIME);

            updateTextureRegion();

            this.animTimer += 1f;

            if (animTimer > ANIM_TIME) {
                this.setTileCoordinates(destinationPosition.x / 32, destinationPosition.y / 32);
                this.finishMove();
            }
        }
    }

    /**
     * Sets up the move, initialising the start position and destination as well as the state of the person.
     * This allows the movement to be smooth and fluid.
     *
     * @param dir the direction that the person is moving in.
     */
    public void initialiseMove(Direction dir)
    {

        this.direction = dir;

        this.startPosition.x = this.tileCoordinates.x * Settings.TILE_SIZE;
        this.startPosition.y = this.tileCoordinates.y * Settings.TILE_SIZE;

        this.destinationPosition.x = this.startPosition.x + (dir.getDx() * Settings.TILE_SIZE);
        this.destinationPosition.y = this.startPosition.y + (dir.getDy() * Settings.TILE_SIZE);
        this.animTimer = 0f;

        this.state = PersonState.WALKING;
    }

    /**
     * Finalises the move by resetting the animation timer and setting the state back to standing.
     * Called when the player is no longer moving.
     */
    public void finishMove()
    {
        animTimer = 0f;

        this.state = PersonState.STANDING;

        updateTextureRegion();
    }


    public void updateTextureRegion()
    {
        float quarter = ANIM_TIME / 4;
        float half = ANIM_TIME / 2;
        float threeQuarters = quarter * 3;

        int row = 1;

        switch (direction) {
            case NORTH:
                row = 3;
                break;
            case EAST:
                row = 2;
                break;
            case SOUTH:
                row = 0;
                break;
            case WEST:
                row = 1;
                break;
        }

        if (animTimer > threeQuarters) {
            setRegion(new TextureRegion(spriteSheet, 64, row * 37, 32, 37));
        } else if (animTimer > half) {
            setRegion(new TextureRegion(spriteSheet, 0, row * 37, 32, 37));
        } else if (animTimer > quarter) {
            setRegion(new TextureRegion(spriteSheet, 32, row * 37, 32, 37));
        } else if (animTimer == 0) {
            setRegion(new TextureRegion(spriteSheet, 0, row * 37, 32, 37));
        }
    }

    public Vector2 getCoords()
    {
        return tempCoordinates;
    }

    /**
     * Used internally to store the coordinates of the person.
     *
     * @param x the x coordinate you wish to store
     * @param y the y coordinate you wish to store
     */
    private void setTempCoords(float x, float y)
    {
        tempCoordinates.x = x;
        tempCoordinates.y = y;
    }


    public Direction getDirection()
    {
        return this.direction;
    }

    public void setDirection(Direction dir)
    {
        this.direction = dir;
    }


    /**
     * This is used to describe the direction the person is currently facing or moving in.
     */
    public enum Direction
    {
        NORTH(0, 1),
        SOUTH(0, -1),
        EAST(1, 0),
        WEST(-1, 0);

        private int dx, dy;

        Direction(int dx, int dy)
        {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx()
        {
            return this.dx;
        }

        public int getDy()
        {
            return this.dy;
        }
    }

    /**
     * The state of the person explains what they are currently doing.
     */
    public enum PersonState
    {
        WALKING,
        STANDING;
    }
}
