package me.lihq.game.people;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tunc on 24/04/2017.
 */
public class StaminaUnitTest {
    private Stamina testStamina;
    private Stamina testStamina2;
    private float action_cost = 20;
    private float default_max = 100;

    @Before
    public void setUp() throws Exception {
        testStamina = new Stamina();
        testStamina2 = new Stamina();
    }

    @After
    public void tearDown() throws Exception {
        testStamina = null;
        testStamina2 = null;
    }


    @Test
    public void move() throws Exception {
        testStamina.enable();
        testStamina.move(1,0);
        assertEquals(99, testStamina.getCurrentStamina(), 1);
    }

    @Test
    public void action() throws Exception {
        assertTrue(testStamina.action());
        testStamina2.enable();
        testStamina2.setCurrentStamina(action_cost - 1);
        assertFalse(testStamina2.action());
    }

    @Test
    public void enable() throws Exception {
        assertFalse(testStamina.checkEnabled());
        testStamina.enable();
        assertTrue(testStamina.checkEnabled());

    }

    @Test
    public void isDepleted() throws Exception {
        assertFalse(testStamina.isDepleted());
        testStamina.setDepleted(true);
        assertTrue(testStamina.isDepleted());
    }

    @Test
    public void setDepleted() throws Exception {
        assertFalse(testStamina.isDepleted());
        testStamina.setDepleted(true);
        assertTrue(testStamina.isDepleted());
        testStamina.setDepleted(false);
        assertFalse(testStamina.isDepleted());
    }

    @Test
    public void reset() throws Exception {
        testStamina.setCurrentStamina(10);
        testStamina.reset();
        assertEquals(default_max ,testStamina.getCurrentStamina(),1);


    }

    @Test
    public void getCurrentStamina() throws Exception {
        testStamina.setCurrentStamina(10);
        assertEquals(10, testStamina.getCurrentStamina(),1);
    }

}