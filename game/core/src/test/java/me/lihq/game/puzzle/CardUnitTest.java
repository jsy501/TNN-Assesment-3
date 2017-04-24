package me.lihq.game.puzzle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tunc on 24/04/2017.
 */
public class CardUnitTest {

    private Card testCard;
    private Card testCard2;
    private Card testCard3;


    @Before
    public void setUp() throws Exception {

        testCard = new Card(1);
        testCard2 = new Card(1);
        testCard3 = new Card(2);
    }

    @After
    public void tearDown() throws Exception {
        testCard = null;
        testCard2 = null;
        testCard3 = null;
    }

    @Test
    public void isMatch() throws Exception {
    assertTrue(testCard.isMatch(testCard2));
    assertFalse(testCard.isMatch(testCard3));

    }

    @Test
    public void getUid() throws Exception {
    assertEquals(1, testCard.getUid());
    assertEquals(2, testCard3.getUid());
    assertEquals(testCard.getUid(),testCard2.getUid());
    }

    @Test
    public void setFlip() throws Exception {
    assertFalse(testCard.isFlip());
    testCard.setFlip(true);
    assertTrue(testCard.isFlip());
    }

    @Test
    public void isFlip() throws Exception {
    assertFalse(testCard.isFlip());
    testCard.setFlip(true);
    assertTrue(testCard.isFlip());
    testCard.setFlip(false);
    assertFalse(testCard.isFlip());
    }

}