package main.Domain.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //game data
    int hallCount;
    long monsterTimer;
    long enchantmentTimer;
    int totalTime;
    int remainingTime;
    int remainingTimePercentageGroup;
    int[] runeLocation;
    //tile handler data
    int[][] mapTileNum0;
    int[][] mapTileNum1;
    int[][] mapTileNum2;
    int[][] mapTileNum3;
    //player data
    int lives;
    int X;
    int Y;
    int invincibleTimer;
    String direction;
    int numLuringGem;
    int numReveal;
    int numCloakOfProtection;
    ArrayList<String> bag;
    //monster data
    ArrayList<Integer> monsterIDs;
    ArrayList<Integer> monsterXs;
    ArrayList<Integer> monsterYs;
    ArrayList<String> monsterDrawDirections;
    ArrayList<Integer> actionLockCountersForFighters;
}
