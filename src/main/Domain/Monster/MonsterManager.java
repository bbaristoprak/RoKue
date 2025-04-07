package main.Domain.Monster;

import main.Domain.Game;
import main.Domain.Monster.Wizard.Wizard;

import java.util.ArrayList;
import java.util.Random;

public class MonsterManager {
    Game game;
    public MonsterManager(Game game) {
        this.game = game;
    }

    /** tested by selin baskurt
     * Requires:
     * - `monsterList` & game is not null.
     * Modifies:
     * - modifies the monsterList by adding a new monster instance.
     * Effects:
     * - Randomly adds a Fighter, Archer or Wizard monster to monsterList.
     * - The monster's position is randomly chosen by the output of game.randomPlace() scaled by game.tileSize.
     */
    public void addMonsterToArray(ArrayList<Monster> monsterList , Game game) {
        Random random = new Random();
        int rand = random.nextInt(3) + 1;
        //int rand = 2;
        int[] arr = game.randomPlace();

        if (rand == 1) {
            monsterList.add(new Fighter(game, new int[]{arr[0]* game.tileSize, arr[1]* game.tileSize}));
        }
        if (rand == 2) {
            monsterList.add(new Archer(game, new int[]{arr[0]* game.tileSize, arr[1]* game.tileSize}));
        }
        if (rand == 3) {
            monsterList.add(new Wizard(game, new int[]{arr[0]* game.tileSize, arr[1]* game.tileSize}));
        }
    }
}