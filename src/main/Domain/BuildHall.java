package main.Domain;

import main.Domain.HallObject.SuperObject;
import main.Domain.Tile.TileHandler;

import javax.swing.*;
import java.io.*;
import java.util.Random;


public class BuildHall implements Runnable{
    int FPS = 60;
    public SuperObject[] objects = new SuperObject[40];
    PlaceObjectHandler placeObjectHandler;
    private int selectedObjectType = -1;
    int maxScreenColumn;
    int maxScreenRow;
    public int tileSize;
    public TileHandler tileHandler;
    Thread gameThread;
    JPanel panel;
    int hallCount;
    public int requiredCount;
    public static TileHandler[] tileHandlers = new TileHandler[4];
    public BuildHall(int maxScreenColumn, int maxScreenRow, int tileSize, TileHandler tileHandler, JPanel panel, int hallCount) {
        this.maxScreenColumn = maxScreenColumn;
        this.maxScreenRow = maxScreenRow;
        this.tileHandler = tileHandler;
        tileHandlers[hallCount] = tileHandler;
        this.tileSize = tileSize;
        this.panel = panel;
        this.placeObjectHandler = new PlaceObjectHandler(this);
        this.hallCount = hallCount;
        switch (hallCount) {
            case 0 -> requiredCount = 6;
            case 1 -> requiredCount = 9;
            case 2 -> requiredCount = 13;
            case 3 -> requiredCount = 17;
        }
        startGameThread();
    }

    public void selectObject(int type) {
        selectedObjectType = type;
    }

    public void handleMouseClick(int x, int y) {
        if (selectedObjectType != -1) {
            setObject(x, y, selectedObjectType);
        }
    }

    public void setObject(int x, int y, int type) {
        placeObjectHandler.putObject(x, y, type);
        switch (hallCount) {
            case 0 -> saveHallToFile("res/map/earth_hall.txt");
            case 1 -> saveHallToFile("res/map/air_hall.txt");
            case 2 -> saveHallToFile("res/map/water_hall.txt");
            case 3 -> saveHallToFile("res/map/fire_hall.txt");
        }
    }

    public boolean validateHall(int hallCount) {
        int count = getObjectsSize();
        if (hallCount == 0) {
            requiredCount -= count;
            return count >= 6;
        }
        else if (hallCount == 1) {
            requiredCount -= count;
            return count >= 9;
        }
        else if (hallCount == 2) {
            requiredCount -= count;
            return count >= 13;
        }
        else if (hallCount == 3) {
            requiredCount -= count;
            return count >= 17;
        }
         return false;
    }

    public void saveHallToFile(String filePath) {
        int[][] existingMap = new int[this.maxScreenColumn][this.maxScreenRow];

        // Read the existing file if it exists, predefined map will added
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                for (int row = 0; row < this.maxScreenRow; row++) {
                    String[] line = reader.readLine().trim().split(" ");
                    for (int col = 0; col < this.maxScreenColumn; col++) {
                        existingMap[col][row] = Integer.parseInt(line[col]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Update the map with the current tile data
        for (int row = 0; row < this.maxScreenRow; row++) {
            for (int col = 0; col < this.maxScreenColumn; col++) {
                if (this.tileHandler.mapTileNum[col][row] != 0) {
                    // Overwrite only non-zero tiles
                    existingMap[col][row] = this.tileHandler.mapTileNum[col][row];
                }
            }
        }

        // Write the updated map back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int row = 0; row < this.maxScreenRow; row++) {
                for (int col = 0; col < this.maxScreenColumn; col++) {
                    writer.write(existingMap[col][row] + " ");
                }
                writer.newLine();
            }
            System.out.println("Map saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double var = 0;
        long lastTime = System.nanoTime();
        long time;

        while (gameThread != null){ // Game loop
            // character movements
            // drawing screen
            time = System.nanoTime();
            var += (time - lastTime) / drawInterval;
            lastTime = time;
            if (var >= 1){
                panel.repaint();
                var--;
            }
        }
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public int getObjectsSize() {
        int count = 0;
        for (SuperObject obj : objects) {
            if (obj != null) count++;
        }
        return count;
    }
    public void randomBuildHall() {
        int num = requiredCount-getObjectsSize();
        for (int i = 0; i<num; i++) {
            Random random = new Random();
            int randX = random.nextInt(2, 24);
            int randY = random.nextInt(3, 16);
            int randObj = random.nextInt(8);
            while (tileHandler.mapTileNum[randX][randY] != 0) {
                randX = random.nextInt(2, 24);
                randY = random.nextInt(3, 16);
            }
            int prevObj = selectedObjectType;
            selectedObjectType = randObj;
            handleMouseClick(randX, randY);
            selectedObjectType = prevObj;
        }
    }

    public boolean repOk() {
        if (objects == null || objects.length > 40) return false;
        if (hallCount < 0 || hallCount > 3) return false;
        if (tileSize <= 0 || maxScreenColumn <= 0 || maxScreenRow <= 0) return false;
        if (requiredCount < 0) return false;
        int expectedCount;
        switch (hallCount) {
            case 0: expectedCount = 6; break;
            case 1: expectedCount = 9; break;
            case 2: expectedCount = 13; break;
            case 3: expectedCount = 17; break;
            default: return false;
        }
        return requiredCount >= expectedCount;
    }





}

