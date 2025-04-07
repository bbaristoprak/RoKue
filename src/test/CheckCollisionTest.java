package test;

import main.Domain.CollisionHandler;
import main.Domain.Game;
import main.Domain.KeyHandler;
import main.Domain.Monster.Archer;
import main.Domain.PlayerObj.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;


import static org.junit.jupiter.api.Assertions.*;

class CheckCollisionTest {

    private Archer testMonster;
    private Player testPlayer;
    private Game testGame;
    private CollisionHandler testCollisionHandler;
    @BeforeEach

    void setUp() {
        // MOCK OBJECTS
        testGame = new Game(1,true);
        KeyHandler keyHandler = new KeyHandler(testGame);

        int[] arr = {0, 0};

        testPlayer = new Player(testGame,keyHandler,true);
        testMonster = new Archer(testGame,arr);
        testCollisionHandler = new CollisionHandler(testGame);

        // SET UP PLAYER
        testPlayer.X = 50;
        testPlayer.Y = 50;
        testPlayer.solidArea = new Rectangle(0, 0, 32, 32); // Set player's solid area
        testGame.player = testPlayer;

        // SET UP MONSTER
        testMonster.X = 60;
        testMonster.Y = 50;
        testMonster.solidArea = new Rectangle(0, 0, 32, 32); // Set object's solid area
        testMonster.direction = "left";
        testMonster.speed = 10;
        testMonster.collisionOn = false;
    }

    /*
     testCollisionDetected: Tests if the method works for simple standing collision
    */
    @Test
    void testCollisionDetected() {
        // Test case where collision should occur
        boolean result = testCollisionHandler.checkCollision(testMonster);
        assertTrue(result, "Collision should be detected.");
        assertTrue(testMonster.collisionOn, "Object's collisionOn flag should be true.");
    }



    /*
     testNoCollision: Tests if the method works for no collision
    */

    @Test
    void testNoCollision() {

        testMonster.X = 100;
        testMonster.Y = 100;

        boolean result = testCollisionHandler.checkCollision(testMonster);
        assertFalse(result, "Collision should not be detected.");
        assertFalse(testMonster.collisionOn, "Object's collisionOn flag should be false.");
    }


    /*
      testDirectionalMovementAffectsCollision: Tests if the method works on different directional
      movement
    */


    @Test
    void testDirectionalMovementAffectsCollision() {
        // Test case where object's movement direction affects collision
        testMonster.direction = "up";
        testMonster.Y = 40; // Position it near the player vertically
        boolean result = testCollisionHandler.checkCollision(testMonster);
        assertTrue(result, "Collision should be detected after movement.");
    }

}