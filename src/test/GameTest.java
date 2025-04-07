package test;

import main.Domain.Game;
import main.Domain.HallObject.SuperObject;
import main.Domain.Tile.TileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private ArrayList<SuperObject> objects;

    @BeforeEach
    public void setUp() {
        game = new Game(0, false);
        game.tileHandler = new TileHandler();
        int[][] array = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 0},
                {0, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 57, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 51, 0, 0, 0, 0, 0, 55, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 53, 0, 0, 0, 0, 0, 56, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0},
                {0, 4, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 15, 16, 10, 10, 10, 10, 10, 10, 8, 0},
                {0, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 13, 14, 7, 7, 7, 7, 7, 7, 7, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int numRows = array.length;
        int numCols = array[0].length;
        int[][] transposedArray = new int[numCols][numRows];
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                transposedArray[x][y] = array[y][x];
            }
        }
        game.tileHandler.mapTileNum = transposedArray;
        objects = new ArrayList<>();
        // Initialize the game with necessary setup
    }

    @Test
    public void testRandomPlaceWithinBounds() {
        int[] coordinates = game.randomPlace();
        int x = coordinates[0];
        int y = coordinates[1];
        assertTrue(x >= 3 && x <24, "X coordinate is out of bounds");
        assertTrue(y >= 3 && y <15, "Y coordinate is out of bounds");
    }

    @Test
    public void testRandomPlaceNotOccupied() {
        int[] coordinates = game.randomPlace();
        int x = coordinates[0];
        int y = coordinates[1];
        assertFalse(game.tileHandler.mapTileNum[x][y] != 0, "Tile is already occupied");
    }

    @Test
    public void testRandomPlaceDifferentResults() {
        int[] coordinates1 = game.randomPlace();
        int[] coordinates2 = game.randomPlace();
        assertFalse(coordinates1[0] == coordinates2[0] && coordinates1[1] == coordinates2[1], "Random places are the same");
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
        // Given a list of objects
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