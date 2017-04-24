package me.lihq.game.people;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import me.lihq.game.GameTester;
import me.lihq.game.models.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
/**
 * Created by Tunc on 24/04/2017.
 */
public class PlayerDialogueUnitTest extends GameTester {
    private Player testPlayer;
    private PlayerDialogue testPlayerDialogue;
    private String testResponse;



    @Before
    public void setUp() throws Exception {
        JsonValue jsonData = new JsonReader().parse(new FileHandle(GameTester.ASSET_FOLDER + "testPlayer.json"));
        TextureAtlas testSprite = new TextureAtlas(GameTester.ASSET_FOLDER + "colin.pack");
        testPlayer = new Player(jsonData, testSprite);
        testPlayer.setCurrentRoom(Mockito.mock(Room.class));
        testPlayerDialogue = new PlayerDialogue(testPlayer);


    }

    @After
    public void tearDown() throws Exception {
        testPlayer = null;
        testPlayerDialogue = null;

    }

    @Test
    public void getQuestionDialogue() throws Exception {

        testResponse = testPlayerDialogue.getQuestionDialogue(QuestionStyle.AGGRESSIVE);
        assertTrue("Aggressive dialogue 1 Aggressive dialogue 2 Aggressive dialogue 3".contains(testResponse));


    }

    @Test
    public void getIntroduction() throws Exception {
        assertEquals("Hello", testPlayerDialogue.getIntroduction());
    }

}