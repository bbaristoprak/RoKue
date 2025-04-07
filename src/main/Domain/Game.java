package main.Domain;

import main.Domain.Enchantment.BagEnchantment;
import main.Domain.Enchantment.Enchantment;
import main.Domain.Enchantment.EnchantmentManager;
import main.Domain.Enchantment.LuringGem;
import main.Domain.HallObject.SuperObject;
import main.Domain.Monster.Monster;
import main.Domain.Monster.MonsterManager;
import main.Domain.PlayerObj.Player;
import main.Domain.Tile.TileHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Runnable {
    public int FPS = 60;
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public TileHandler tileHandler;
    public CollisionHandler collisionHandler;
    public Thread gameThread;
    public Player player;
    public ArrayList<SuperObject> objects;
    public ArrayList<GameListener> listeners;
    transient JPanel panel;
    public int hallCount;
    public int gameState;
    public int totalTime;
    public int remainingTime;
    public int randomX;
    public int randomY;
    public ArrayList<Monster> monsterList;
    public ArrayList<Enchantment> enchantmentList;
    public MonsterManager monsterManager;
    public EnchantmentManager enchantmentManager;
    public boolean isRuneFound;
    public boolean revealActive;
    public boolean bpressed;
    public boolean gemThrown;
    public String gemThrownLoc;
    public int gemX;
    public int gemY;
    public int remainingTimePercentageGroup;
    public SuperObject objectWithRune;
    public long monsterTimer;
    public long enchantmentTimer;

    public Game(int hallCount, Boolean isLoaded){
        this.hallCount = hallCount;
        this.isRuneFound = false;
        if (!isLoaded) {
            remainingTimePercentageGroup = 2;
            tileHandler = BuildHall.tileHandlers[hallCount];
        }
        gameState = 1;
        collisionHandler = new CollisionHandler(this);
        monsterManager = new MonsterManager(this);
        enchantmentManager = new EnchantmentManager(this);
        monsterList = new ArrayList<>();
        listeners = new ArrayList<>();
        objects = new ArrayList<>();
        enchantmentList = new ArrayList<>();
        this.isRuneFound = false;
        this.revealActive = false;
        this.gemThrown = false;
        remainingTimePercentageGroup = 2;
    }
    public void setMapData(int[][] mapData) {
        tileHandler.setMapTileNum(mapData);
    }



    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setTime() {
        totalTime = objects.size()*5;
        remainingTime = totalTime;
    }
    public void setObjects(SuperObject[] loadedObjects) {
        objects.clear();
        for (SuperObject obj : loadedObjects) {
            if (obj != null) {
                objects.add(obj);
            }
        }
    }

    public void setIsRuneFound(boolean b){
        this.isRuneFound = b;
        if (b) {
            notifyRuneFound();
        }
    }
    /**
     * Spawns a rune by randomly selecting a single SuperObject from the hall's objects list and assigning it the rune.
     *
     * Requires:
     * - `objects` must not be null.
     * - `objects` must contain at least one SuperObject.
     *
     * Modifies:
     * - The `hasRune` attribute of the selected SuperObject.
     * - The `hasRune` attribute of the previously selected SuperObject (if any).
     * - The `objectWithRune` field.
     *
     * Effects:
     * - Selects a random SuperObject from the list and sets its `hasRune` attribute to true.
     * - Ensures that at most one object has `hasRune = true` at any time.
     * - Updates the `objectWithRune` reference to point to the newly selected object.
     * - If `objects` is empty or null, throws an IllegalArgumentException.
     */
    public void spawnRune(ArrayList<SuperObject> objects){
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Object list cannot be empty.");
        }

        if (objectWithRune != null) {
            objectWithRune.hasRune = false;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(objects.size());
        SuperObject randomObject = objects.get(randomIndex);
        randomObject.setHasRune(true);
        objectWithRune = randomObject;
        //System.out.println(randomObject.getX() + "," + randomObject.getY());
    }


    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double var = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null){ // Game loop
            // character movements
            // drawing screen
            currentTime = System.nanoTime();
            var += (currentTime - lastTime) / drawInterval;
            if (gameState == 1) {
                timer += (currentTime - lastTime);
                monsterTimer += (currentTime - lastTime);
                enchantmentTimer += (currentTime - lastTime);
            }
            lastTime = currentTime;
            if (var >= 1){
                if (gameState == 1) {
                    player.update();
                    for (Monster monster: monsterList) {
                        monster.update();
                    }
                }
                panel.repaint();
                var--;
            }

            if (gameState == 1) {
                if (timer >= 1000000000) {
                    System.out.println(objectWithRune.getX() + "," + objectWithRune.getY());
                    remainingTime--;
                    notifyChangeTime();
                    updateRemainingTimePercentage();
                    timer = 0;
                }

                if (monsterTimer >= 8000000000.0) {
                    monsterManager.addMonsterToArray(monsterList, this);
                    monsterTimer = 0;
                }

                if (enchantmentTimer >= 4000000000.0){
                    enchantmentManager.addEnchantmentToArray(enchantmentList, this);
                    enchantmentTimer = 0;
                }
            }


        }
    }
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void searchForRune(int x, int y) {
        if (tileHandler.mapTileNum[x][y] >= 50){
            for (SuperObject obj : objects){
                if (obj.x == x & obj.y == y){
                    if (player.isNear(obj)){
                        if (obj.hasRune){
                            setIsRuneFound(true);
                        }
                    }
                }
            }
        }
    }
    /**
     * Requires:
     *  - `tileHandler` is initialized, and `tileHandler.mapTileNum` contains valid tile data.
     *  - The map represented by `tileHandler.mapTileNum` is non-empty.
     *  - Valid tiles are represented by non-zero values in `mapTileNum`.
     *
     * Modifies:
     *  - None.
     *
     * Effects:
     *  - Returns an array `[randX, randY]` representing a randomly chosen tile coordinate.
     *  - The returned coordinate satisfies the following conditions:
     *    - It corresponds to a valid, walkable tile (zero value in `mapTileNum`).
     *    - It does not overlap with the current position of the player (if the player is initialized).
     *    - It does not overlap with any existing monster's position in `monsterList` (if monsters exist).
     */
    public int[] randomPlace() {
        Random random = new Random();
        int randX = random.nextInt(3, 24);
        int randY = random.nextInt(3, 15);
        int playerXTile = -1;
        int playerYTile = -1;
        if (player != null) {
            playerXTile = player.X / tileSize;
            playerYTile = player.Y / tileSize;
        }
        while (true) {
            if (tileHandler.mapTileNum[randX][randY] != 0) {
                randX = random.nextInt(3, 24);
                randY = random.nextInt(3, 15);
                continue;
            }
            if ((playerXTile == randX) && (playerYTile == randY)) {
                randX = random.nextInt(3, 24);
                randY = random.nextInt(3, 15);
                continue;
            }
            if (!monsterList.isEmpty()) {
                boolean isOccupied = false;
                for (Monster monster : monsterList) {
                    if (monster.X / tileSize == randX && monster.Y / tileSize == randY) {
                        isOccupied = true;
                        break;
                    }
                }
                if (isOccupied) {
                    randX = random.nextInt(3, 24);
                    randY = random.nextInt(3, 15);
                    continue;
                }
            }
            break;
        }
        return new int[]{randX, randY};
    }
    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeGameListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void notifyRuneLocationChanged() {
        for (GameListener listener : listeners) {
            listener.runeRelocated();
        }
    }

    private void notifyRuneFound() {
        for (GameListener listener : listeners) {
            listener.onRuneFound();
        }
    }

    private void notifyChangeTime() {
        for (GameListener listener : listeners) {
            listener.timeChanged(remainingTime);
        }
    }

    public void notifyLivesChange() {
        for (GameListener listener : listeners) {
            listener.livesChanged(player.lives);
        }
    }

    public void notifyTimePercentageGroupUpdate() {
        for (GameListener listener : listeners) {
            listener.onTimePercentageGroupUpdate(remainingTimePercentageGroup);
        }
    }

    public void notifyEnchantmentAdded(String enchantmentName) {
        for (GameListener listener : listeners) {
            listener.enchantmentAdded(enchantmentName);
        }
    }

    public void notifyEnchantmentRemoved(String enchantmentName) {
        for (GameListener listener : listeners) {
            listener.enchantmentRemoved(enchantmentName);
        }
    }

    public double getRemainingTimePercentage() {
        return (double) remainingTime / totalTime * 100;
    }

    public void movePlayerTo(int x, int y) {
        player.setX(x*tileSize);
        player.setY(y*tileSize);
    }

    /**
     * Requires:
     *totalTime is bigger than > 0.
     *remainingTime is bigger that or equals to >= 0.
     *remainingTimePercentageGroup must have one of the 0, 1, or 2 values.
     *Any listeners (our GameListener) must be already registered and has to be notified.
     *
     * Modifies:
     *The value of remainingTimePercentageGroup if the new percentage is from different category.
     *If the group changes triggers the notifyTimePercentageGroupUpdate() method.
     *
     * Effects:
     *(remainingTime / totalTime) * 100 = remaining time ratio , is computed.
     *Groups: (are specified)
     * 0 => <= 30%
     * 1 => 31% to 70%
     * 2 => > 70%
     *
     *remainingTimePercentageGroup, is updated and notified the listeners if the new computed group is different from the current.
     */
    public void updateRemainingTimePercentage() {
        double temp = getRemainingTimePercentage();
        int newGroup;
        if (temp <= 30) {
            newGroup = 0;
        } else if (temp <= 70) {
            newGroup = 1;
        } else {
            newGroup = 2;
        }

        if (newGroup != remainingTimePercentageGroup) {
            remainingTimePercentageGroup = newGroup;
            notifyTimePercentageGroupUpdate();
        }
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setRemainingTimePercentageGroup(int remainingTimePercentageGroup) {
        this.remainingTimePercentageGroup = remainingTimePercentageGroup;
    }

    public void clickEnchantment(int tileX, int tileY) {
        for (int i = enchantmentList.size() - 1; i >= 0; i--){
            Enchantment enchantment = enchantmentList.get(i);
            if (enchantment.getX()/tileSize == tileX & enchantment.getY()/tileSize == tileY){
                enchantment.onClick(this);
                enchantmentList.remove(i);
                if (enchantment instanceof BagEnchantment) {
                    notifyEnchantmentAdded(enchantment.toString());
                }
            }
        }

    }


    public void randomX(){
        Random random = new Random();
        randomX = random.nextInt(-3, 1);
    }

    public void randomY(){
        Random random = new Random();
        randomY = random.nextInt(-3, 1);
    }
}