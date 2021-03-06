package me.lihq.game.people;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import me.lihq.game.GameTester;
import me.lihq.game.models.Clue;
import me.lihq.game.models.Vector2Int;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NPCUnitTests extends GameTester {
    private Npc testNpc;
    private Clue testClue;

    @Before
    public void setUp() throws Exception {
        Json json = new Json();
        JsonValue npcJsonData = new JsonReader().parse(new FileHandle(GameTester.ASSET_FOLDER + "testNPC.json"));
        Array<JsonValue> npcJsonDataArray = json.readValue(Array.class, npcJsonData);
        TextureAtlas testSprite = new TextureAtlas(GameTester.ASSET_FOLDER + "colin.pack");

        TextureAtlas clueGlint = new TextureAtlas(GameTester.ASSET_FOLDER + "clueGlint.pack");
        JsonValue clueJsonData = new JsonReader().parse(new FileHandle(GameTester.ASSET_FOLDER + "testClue.json"));
        Array<JsonValue> clueJsonDataArray = json.readValue(Array.class, clueJsonData);

        testClue = new Clue(clueJsonDataArray.get(0), clueGlint);

        testNpc = new Npc(npcJsonDataArray.get(0), testSprite);
    }

    @After
    public void tearDown() throws Exception {
        testNpc = null;
    }

    @Test
    public void getName() throws Exception{
        assertNotNull(testNpc.getName());
        assertEquals("getting the name of the Npc failing", "testNPC1", testNpc.getName());
    }

    @Test
    public void getPersonality() throws Exception{
        assertNotNull(testNpc.getPersonality());
        assertEquals(Personality.AGGRESSIVE, testNpc.getPersonality());
    }

    @Test
    public void isKiller() throws Exception {
        assertNotNull(testNpc.isKiller());
        assertFalse(testNpc.isKiller());
    }

    @Test
    public void setKiller() throws Exception {
        assertFalse(testNpc.isKiller());

        testNpc.setMurderer(true);

        assertTrue(testNpc.isKiller());
    }

    @Test
    public void isVictim() throws Exception {
        assertNotNull(testNpc.isVictim());
        assertFalse(testNpc.isVictim());
    }

    @Test
    public void setVictim() throws Exception {
        assertFalse(testNpc.isVictim());

        testNpc.setVictim(true);

        assertTrue(testNpc.isVictim());
    }

    @Test
    public void getExhaustedClues() throws Exception {
        assertNotNull(testNpc.getQuestionedClueArray());
        assertTrue(testNpc.getQuestionedClueArray().size == 0);
    }

    @Test
    public void addExhaustedClue() throws Exception {
        assertTrue(testNpc.getQuestionedClueArray().size == 0);

        testNpc.addQuestionedClue(testClue);

        assertTrue(testNpc.getQuestionedClueArray().contains(testClue,true) && testNpc.getQuestionedClueArray().size == 1);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(testNpc.getId());
        assertEquals("id is not correct", 1, testNpc.getId());
    }

    @Test
    public void getDescription() throws Exception {
        assertNotNull(testNpc.getDescription());
        assertEquals("description is not correct", "test description", testNpc.getDescription());
    }

    @Test
    public void setFalseAccused() throws Exception {
        assertFalse(testNpc.isFalseAccused());

        testNpc.setFalseAccused(true);

        assertTrue(testNpc.isFalseAccused());
    }

    @Test
    public void getFalselyAccused() throws Exception {
        assertNotNull(testNpc.isFalseAccused());
        assertFalse(testNpc.isFalseAccused());
    }

    @Test
    public void getTilePosition() throws Exception {
        assertNotNull(testNpc.getTilePosition());
        assertTrue(testNpc.getTilePosition() instanceof Vector2Int);
    }

    @Test
    public void setTilePosition() throws Exception {
        assertTrue(testNpc.getTilePosition().getX() == 0 && testNpc.getTilePosition().getY() == 0);
        testNpc.setTilePosition(1,1);
        assertTrue(testNpc.getTilePosition().getX()==1 && testNpc.getTilePosition().getY()==1);
    }
}

