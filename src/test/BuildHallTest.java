package test;

import static org.junit.jupiter.api.Assertions.*;

import main.Domain.BuildHall;
import main.Domain.Tile.TileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class BuildHallTest {
    BuildHall buildHall;
    TileHandler tileHandler;

    @BeforeEach
    void setUp() {
        tileHandler = new TileHandler(); // Example grid
        JPanel panel = new JPanel();
        buildHall = new BuildHall(25, 18, 16, tileHandler, panel, 0);
    }
    // Verifies that the repOk method returns true for a valid BuildHall instance.
    @Test
    void testRepOkValid() {
        assertTrue(buildHall.repOk(), "repOk should return true for valid BuildHall.");
    }

    //Checks if an object is correctly placed on the map when setObject is called.
    @Test
    void testSetObject() {
        buildHall.selectObject(1);
        buildHall.setObject(15, 7, 1);
        assertEquals(51, buildHall.tileHandler.mapTileNum[15][7], "Object type should be set correctly in the map.");
    }

    @Test
    void testValidateHall() {
        buildHall.randomBuildHall();
        assertTrue(buildHall.validateHall(0), "Validation should pass when the required number of objects is reached.");
    }

    @Test
    void testRandomBuildHall() {
        buildHall.randomBuildHall();
        int placedObjects = buildHall.getObjectsSize();
        assertTrue(placedObjects >= 6, "Randomly built hall should have at least 6 objects.");
    }
}
