package test;

import main.Domain.Game;
import main.Domain.Monster.*;
import main.Domain.Monster.Archer;
import main.Domain.Monster.Fighter;
import main.Domain.Monster.Wizard.Wizard;
import main.Domain.Tile.TileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterManagerTest {

    private Game game;
    private MonsterManager manager;
    private ArrayList<Monster> monsterList;

    @BeforeEach
    void setUp() {
        TileHandler tileHandler = new TileHandler();
        int[][] mapTileNum = new int[25][18];
        tileHandler.setMapTileNum(mapTileNum);

        game = new Game(1, false);
        game.tileHandler = tileHandler;
        manager = new MonsterManager(game);
        monsterList = new ArrayList<>();
    }

    /**
     * checks for correct addition of monster to monsterlist
     */
    @Test
    void AddsMonstersToList() {
        manager.addMonsterToArray(monsterList, game);
        assertEquals(1, monsterList.size(), "Monster should be added to the list");
    }

    /**
     * checks validity of the added monster type
     */
    @Test
    void AddsCorrectMonsterType() {
        manager.addMonsterToArray(monsterList, game);
        Monster addedMonster = monsterList.getFirst();

        switch (addedMonster) {
            case Fighter fighter -> assertTrue(true, "The added monster is of type Fighter");
            case Archer archer -> assertTrue(true, "The added monster is of type Archer");
            case Wizard wizard -> assertTrue(true, "The added monster is of type Wizard");
            case null, default -> fail("Unknown monster type added");
        }
    }

    /**
     * checks for correct positioning of added monster
     */
    @Test
    void AddsMonsterWithCorrectPosition() {
        Game testGame = new Game(1, false) {
            @Override
            public int[] randomPlace() {return new int[]{7, 8};}
        };
        TileHandler tileHandler = new TileHandler();
        int[][] mapTileNum = new int[25][18];
        tileHandler.setMapTileNum(mapTileNum);
        testGame.tileHandler = tileHandler;

        MonsterManager testManager = new MonsterManager(testGame);
        testManager.addMonsterToArray(monsterList, testGame);

        Monster addedMonster = monsterList.getFirst();
        int[] random_p = {7 * testGame.tileSize, 8 * testGame.tileSize};
        int[] actual_p = {addedMonster.getX(), addedMonster.getY()};

        assertArrayEquals(random_p, actual_p, "Monster position should match the randomly selected position");



    }
}
