package me.lihq.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.*;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * This class defines the assetLoader that the game uses.
 */
public class AssetLoader {
    public static final String ASSET_FOLDER = "assets/";
    /**
     * Parameters needed for AssetLoader object:
     *
     * manager - a LibGDX AssetManager object used to manage assets
     * arrowAtlas -  These TextureRegions store the 4 different directions that the room changing arrows can face.
     * playerSpriteSheetArray, npcSpriteSheetMapArray - Sprite sheets for abstract person objects
     * mapArray - map Data
     * roomTagBorder - Texture for the RoomTag
     * roomTagFont - Default roomTagFont for the game. Used in RoomTag
     * clueGlint - Sprite sheet for the clue glint to be drawn where a clue is hidden
     * menuSkin - skin for main menu
     * uiskin - skin for UI elements
     * npcJsonData, playerJsonData, clueJsonData - Data loaded in from json for npcs, players and clues
     */
    private AssetManager manager;

    public TextureAtlas loremIpsomSplash;
    public Texture teamNoNameSplash;

    public Texture titleImage;

    public TextureAtlas arrowAtlas;

    public ArrayMap<Integer, TextureAtlas> playerSpriteSheetArray;
    public ArrayMap<Integer, TextureAtlas> npcSpriteSheetMapArray;

    public Array<TiledMap> mapArray;
    public TiledMap secretRoom;

    public Texture roomTagBorder;

    public BitmapFont roomTagFont;

    public TextureAtlas clueGlint;

    public Skin uiSkin;

    public JsonValue npcJsonData;
    public JsonValue playerJsonData;
    public JsonValue clueJsonData;


    public Array<Texture> cardTextureArray;

    public Music menuMusic;
    public Sound door;
    public Music endingTune;
    public Sound footstep;
    public Sound menuClick;
    public Sound menuMouseOver;
    public Music roomTone;
    public Sound waterDrop;

    /**
     * Constructor to build AssetLoader object
     */
    public AssetLoader(){
        manager = new AssetManager();
        mapArray = new Array<>();
        npcSpriteSheetMapArray = new ArrayMap<>();
        playerSpriteSheetArray = new ArrayMap<>();

        cardTextureArray = new Array<>();
    }

    /**
     * loads asset into manager for the splash screen
     */
    public void loadSplashAssets(){
        manager.load(ASSET_FOLDER + "loremIpsomSplash.pack",TextureAtlas.class);
        manager.load(ASSET_FOLDER + "teamNoNameSplash.png",Texture.class);
        manager.load(ASSET_FOLDER + "music/menu.ogg", Music.class);
    }

    /**
     * retrieves asset from manager for the splash screen
     */
    public void assignSplashAssets(){
        loremIpsomSplash = manager.get(ASSET_FOLDER + "loremIpsomSplash.pack");
        teamNoNameSplash = manager.get(ASSET_FOLDER + "teamNoNameSplash.png");
        menuMusic = manager.get(ASSET_FOLDER + "music/menu.ogg");
    }

    /**
     * loads assets into manager for SFX
     */
    private void loadSoundAssets(){
        manager.load(ASSET_FOLDER + "sounds/door.ogg", Sound.class);
        manager.load(ASSET_FOLDER + "music/endingTune.ogg", Music.class);
        manager.load(ASSET_FOLDER + "sounds/footstep.ogg", Sound.class);
        manager.load(ASSET_FOLDER + "sounds/menuClick.ogg", Sound.class);
        manager.load(ASSET_FOLDER + "music/roomTone.ogg", Music.class);
    }

    /**
     * retrieves assets from manager for SFX
     */
    private void assignSoundAssets(){
        door = manager.get(ASSET_FOLDER + "sounds/door.ogg");
        endingTune = manager.get(ASSET_FOLDER + "music/endingTune.ogg");
        footstep = manager.get(ASSET_FOLDER + "sounds/footstep.ogg");
        menuClick = manager.get(ASSET_FOLDER + "sounds/menuClick.ogg");
        roomTone = manager.get(ASSET_FOLDER + "music/roomTone.ogg");
    }
    /**
     * loads in game assets into manager
     */
    public void loadGameAssets(){
        manager.load(ASSET_FOLDER + "titleImage.png", Texture.class);

        manager.load(ASSET_FOLDER + "skin/comic-ui.json", Skin.class);

        manager.load(ASSET_FOLDER + "arrows.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "clueGlint.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "roomTagBorder.png", Texture.class);

        manager.load(ASSET_FOLDER + "people/player/alfred.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/player/phoebe.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/player/sherlock.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/player/steiner.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/player/victoria.pack", TextureAtlas.class);

        manager.load(ASSET_FOLDER + "people/NPCs/colin.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/diana.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/lily.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/mary.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/mike.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/will.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/david.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/julie.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/sophie.pack", TextureAtlas.class);
        manager.load(ASSET_FOLDER + "people/NPCs/tom.pack", TextureAtlas.class);

        manager.load(ASSET_FOLDER + "puzzle/1.png", Texture.class);
        manager.load(ASSET_FOLDER + "puzzle/2.png", Texture.class);
        manager.load(ASSET_FOLDER + "puzzle/3.png", Texture.class);
        manager.load(ASSET_FOLDER + "puzzle/4.png", Texture.class);
        manager.load(ASSET_FOLDER + "puzzle/5.png", Texture.class);
        manager.load(ASSET_FOLDER + "puzzle/6.png", Texture.class);
        manager.load(ASSET_FOLDER + "puzzle/qm.png", Texture.class);

        loadSoundAssets();


        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(ASSET_FOLDER + "maps/computerRoom.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/island.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/kitchen.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/lakeHouse.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/mainRoom.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/outside.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/pod.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/portersOffice.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/rch037.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/toilet.tmx",TiledMap.class);
        manager.load(ASSET_FOLDER + "maps/storageRoom.tmx", TiledMap.class);
    }

    /**
     *  retrieves assets from manager and assigns them correctly
     */
    public void assignGameAssets()
    {
        titleImage = manager.get(ASSET_FOLDER + "titleImage.png");

        //roomTagFont init
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(ASSET_FOLDER + "fonts/VT323-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 50;
        roomTagFont = generator.generateFont(parameter);
        generator.dispose();

        playerJsonData = new JsonReader().parse(Gdx.files.internal(ASSET_FOLDER + "player.json"));
        npcJsonData = new JsonReader().parse(Gdx.files.internal(ASSET_FOLDER + "npc.json"));
        clueJsonData = new JsonReader().parse(Gdx.files.internal(ASSET_FOLDER + "clue.json"));

        // sprite sheet assign
        playerSpriteSheetArray.put(1,manager.get(ASSET_FOLDER + "people/player/alfred.pack"));
        playerSpriteSheetArray.put(2,manager.get(ASSET_FOLDER + "people/player/phoebe.pack"));
        playerSpriteSheetArray.put(3,manager.get(ASSET_FOLDER + "people/player/sherlock.pack"));
        playerSpriteSheetArray.put(4,manager.get(ASSET_FOLDER + "people/player/steiner.pack"));
        playerSpriteSheetArray.put(5,manager.get(ASSET_FOLDER + "people/player/victoria.pack"));


        //map key is the npc id
        npcSpriteSheetMapArray.put(1, manager.get(ASSET_FOLDER + "people/NPCs/colin.pack"));
        npcSpriteSheetMapArray.put(2, manager.get(ASSET_FOLDER + "people/NPCs/diana.pack"));
        npcSpriteSheetMapArray.put(3, manager.get(ASSET_FOLDER + "people/NPCs/lily.pack"));
        npcSpriteSheetMapArray.put(4, manager.get(ASSET_FOLDER + "people/NPCs/mary.pack"));
        npcSpriteSheetMapArray.put(5, manager.get(ASSET_FOLDER + "people/NPCs/mike.pack"));
        npcSpriteSheetMapArray.put(6, manager.get(ASSET_FOLDER + "people/NPCs/will.pack"));
        npcSpriteSheetMapArray.put(7, manager.get(ASSET_FOLDER + "people/NPCs/david.pack"));
        npcSpriteSheetMapArray.put(8, manager.get(ASSET_FOLDER + "people/NPCs/julie.pack"));
        npcSpriteSheetMapArray.put(9, manager.get(ASSET_FOLDER + "people/NPCs/sophie.pack"));
        npcSpriteSheetMapArray.put(10, manager.get(ASSET_FOLDER + "people/NPCs/tom.pack"));

        //puzzle card texture assign
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/1.png"));
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/2.png"));
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/3.png"));
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/4.png"));
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/5.png"));
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/6.png"));
        cardTextureArray.add(manager.get(ASSET_FOLDER + "puzzle/qm.png"));

        // map assign
        mapArray.add(manager.get(ASSET_FOLDER + "maps/computerRoom.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/island.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/kitchen.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/lakeHouse.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/mainRoom.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/outside.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/pod.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/portersOffice.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/rch037.tmx"));
        mapArray.add(manager.get(ASSET_FOLDER + "maps/toilet.tmx"));

        //secret room assign
        secretRoom = manager.get(ASSET_FOLDER + "maps/storageRoom.tmx");

        //arrow texture assign
        arrowAtlas = manager.get(ASSET_FOLDER + "arrows.pack");

        //room tag border texture assign
        roomTagBorder = manager.get(ASSET_FOLDER + "roomTagBorder.png");

        //clue glint animation init
        clueGlint = manager.get(ASSET_FOLDER + "clueGlint.pack");

        uiSkin = manager.get(ASSET_FOLDER + "skin/comic-ui.json");

        assignSoundAssets();

        //menu skin init
        initSkin();
    }

    /**
     * Builds menu Skin and ui skin from assets
     */
    private void initSkin()
    {
        //used in InfoWindow class
        Label.LabelStyle infoStyle = new Label.LabelStyle(getFontWithSize(30), Color.BLACK);
        uiSkin.add("dialog", infoStyle);

        //used in ConversationSpeechBubble class
        Label.LabelStyle speechNameStyle = new Label.LabelStyle(getFontWithSize(20), Color.RED);
        uiSkin.add("speechName", speechNameStyle);

        //used in ConversationSpeechBubble class
        Label.LabelStyle speechDialogueStyle = new Label.LabelStyle(getFontWithSize(25), Color.BLACK);
        uiSkin.add("speechDialogue", speechDialogueStyle);

        //used in InteractionSelectionBubble class and QuestionStyleSelectionBubble class
        TextButton.TextButtonStyle buttonBubbleStyle = new TextButton.TextButtonStyle();
        buttonBubbleStyle.font = getFontWithSize(30);
        buttonBubbleStyle.fontColor = Color.BLACK;
        buttonBubbleStyle.up = uiSkin.getDrawable("button");
        buttonBubbleStyle.over = uiSkin.getDrawable("button-highlighted");
        buttonBubbleStyle.down = uiSkin.getDrawable("button-pressed");
        uiSkin.add("buttonBubble", buttonBubbleStyle);
    }

    /**
     * This method gets the default roomTagFont but at the requested size
     *
     * @param size - The size you want the roomTagFont to be
     * @return (BitmapFont) the resulting roomTagFont
     */
    public BitmapFont getFontWithSize(int size)
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(ASSET_FOLDER + "fonts/VT323-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    /**
     * getter for manager
     * @return returns manager
     */
    public AssetManager getManager() {
        return manager;
    }

    /**
     * Called when AssetLoader is disposed ensures manager object is also disposed
     */
    public void dispose(){
        manager.dispose();
    }
}
