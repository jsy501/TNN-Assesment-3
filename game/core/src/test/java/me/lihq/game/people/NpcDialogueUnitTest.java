package me.lihq.game.people;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import me.lihq.game.GameTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Tunc on 24/04/2017.
 */
public class NpcDialogueUnitTest extends GameTester {
    private Npc testNpc;
    private NpcDialogue testDialogue;

    @Before
    public void setUp() throws Exception {
        Json json = new Json();
        JsonValue npcJsonData = new JsonReader().parse(new FileHandle(GameTester.ASSET_FOLDER + "testNPC.json"));
        Array<JsonValue> npcJsonDataArray = json.readValue(Array.class, npcJsonData);
        TextureAtlas testSprite = new TextureAtlas(GameTester.ASSET_FOLDER + "colin.pack");

        testNpc = new Npc(npcJsonDataArray.get(0), testSprite);
        testDialogue = new NpcDialogue(testNpc);
    }

    @After
    public void tearDown() throws Exception {
        testDialogue = null;
        testNpc = null;
    }

    @Test
    public void getFailResponseArray() throws Exception {
        assertEquals("Sorry I am a little busy for questions right now.",testDialogue.getFailResponseArray().get(0));
        assertEquals("And why do you think I would help you.",testDialogue.getFailResponseArray().get(1));
    }



    @Test
    public void getIntroduction() throws Exception {
        assertEquals("Hello young detective!",testDialogue.getIntroduction());

    }

}