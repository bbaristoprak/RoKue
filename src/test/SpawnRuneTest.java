package test;

import static org.junit.jupiter.api.Assertions.*;
import main.Domain.Game;
import main.Domain.HallObject.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SpawnRuneTest {
    private Game game;
    private ArrayList<SuperObject> objects;

    @BeforeEach
    void setUp() {
        game = new Game(1, false);
        objects = new ArrayList<>();
    }

    @Test
    void testRuneAssignedToOneObject() {
        for (int i = 0; i < 5; i++) {
            objects.add(new SuperObject());
        }

        game.spawnRune(objects);

        long count = objects.stream().filter(obj -> obj.hasRune).count();
        assertEquals(1, count, "Only one object should have a rune assigned.");
        assertNotNull(game.objectWithRune, "The objectWithRune reference should not be null.");
    }

    @Test
    void testRuneChangesOnMultipleCalls() {

        for (int i = 0; i < 5; i++) {
            objects.add(new SuperObject());
        }

        game.spawnRune(objects);
        SuperObject firstRuneObject = game.objectWithRune;

        game.spawnRune(objects);
        SuperObject secondRuneObject = game.objectWithRune;

        long count = objects.stream().filter(obj -> obj.hasRune).count();
        assertEquals(1, count, "Only one object should have a rune assigned.");

        assertNotNull(firstRuneObject);
        assertNotNull(secondRuneObject);
        assertNotEquals(firstRuneObject, secondRuneObject,
                "The rune should be assigned to a different object on consecutive calls (unless randomly chosen the same).");
    }


    @Test
    void testNoExceptionForEmptyList() {

        assertThrows(IllegalArgumentException.class, () -> game.spawnRune(objects),
                "Method should throw an exception when list is empty.");
    }
}