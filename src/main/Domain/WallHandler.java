package main.Domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WallHandler {
    // Singleton instance
    private static WallHandler instance;

    // Private constructor to prevent external instantiation
    private WallHandler() {
    }

    // Public method to provide access to the singleton instance
    public static WallHandler getInstance() {
        if (instance == null) {
            synchronized (WallHandler.class) {
                if (instance == null) { // Double-checked locking for thread safety
                    instance = new WallHandler();
                }
            }
        }
        return instance;
    }

    public void resetMapFile(String filePath) {
        String[] mapData = {
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                "0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 5 0",
                "0 2 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 6 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 12 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 0",
                "0 4 10 10 10 10 10 10 10 10 10 10 10 10 10 15 16 10 10 10 10 10 10 8 0",
                "0 7 7 7 7 7 7 7 7 7 7 7 7 7 7 13 14 7 7 7 7 7 7 7 0 0",
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0"
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : mapData) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Map reset in file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error resetting map file: " + e.getMessage());
        }
    }
    public void resetAllMaps(){
        resetMapFile("res/map/earth_hall.txt");
        resetMapFile("res/map/air_hall.txt");
        resetMapFile("res/map/water_hall.txt");
        resetMapFile("res/map/fire_hall.txt");
    }
}
