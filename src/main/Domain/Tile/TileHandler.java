package main.Domain.Tile;

import main.Domain.HallObject.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class TileHandler {
    public int maxScreenRow = 18;
    public int maxScreenColumn = 25;
    int tileSize = 48;
    public Tile[] tiles;
    public int[][] mapTileNum;

    public TileHandler() {
        this.tiles = new Tile[100];
        this.mapTileNum = new int[maxScreenColumn][maxScreenRow];
        getTileImage();
    }
    public void setMapTileNum(int[][] mapData) {
        this.mapTileNum = mapData;
    }


    public void getTileImage () {
        try {
            tiles[0] = new Tile();
            tiles[0].collision = false;

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_top_left1.png")));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_top_left2.png")));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_bot_left1.png")));
            tiles[3].collision = true;

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_bot_left2.png")));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_top_right1.png")));
            tiles[5].collision = true;

            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_top_right2.png")));
            tiles[6].collision = true;

            tiles[7] = new Tile();
            tiles[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_bot_right1.png")));
            tiles[7].collision = true;

            tiles[8] = new Tile();
            tiles[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_bot_right2.png")));
            tiles[8].collision = true;

            tiles[9] = new Tile();
            tiles[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_default1.png")));
            tiles[9].collision = true;

            tiles[10] = new Tile();
            tiles[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_default2.png")));
            tiles[10].collision = true;


            tiles[11] = new Tile();
            tiles[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_right.png")));
            tiles[11].collision = true;

            tiles[12] = new Tile();
            tiles[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/wall_left.png")));
            tiles[12].collision = true;

            tiles[13] = new Tile();
            tiles[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/gate_bot_left.png")));
            tiles[13].collision = true;

            tiles[14] = new Tile();
            tiles[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/gate_bot_right.png")));
            tiles[14].collision = true;

            tiles[15] = new Tile();
            tiles[15].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/gate_top_left.png")));
            tiles[15].collision = true;

            tiles[16] = new Tile();
            tiles[16].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map/gate_top_right.png")));
            tiles[16].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public void loadMap(String filePath) {
//        try {
//            InputStream is = getClass().getResourceAsStream(filePath);
//            if (is == null) {
//                System.out.println("loadMap Error: Map file not found - " + "RoKue/res" + filePath);
//                return;
//            }
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//            int col = 0;
//            int row = 0;
//
//            while (row < this.maxScreenRow) {
//                String line = br.readLine();
//                String[] numbers = line.split(" ");
//                while (col < this.maxScreenColumn) {
//                    int num = Integer.parseInt(numbers[col]);
//                    mapTileNum[col][row] = num;
//                    col++;
//                }
//                if (col == this.maxScreenColumn) {
//                    col = 0;
//                    row++;
//                }
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public SuperObject[] loadObjectsFromMap(String filePath) {
        ArrayList<SuperObject> loadedObjects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int row = 0;
            String line;

            while ((line = br.readLine()) != null) {
                String[] cells = line.split(" ");
                for (int col = 0; col < cells.length; col++) {
                    int value = Integer.parseInt(cells[col]);
                    if (value >= 50) {
                        SuperObject obj = switch (value-50) {//to get the objects
                            case 0 -> new Obj_Chest();
                            case 1 -> new Obj_BoxSmall();
                            case 2 -> new Obj_BoxBig();
                            case 3 -> new Obj_Ladder();
                            case 4 -> new Obj_Skull();
                            case 5 -> new Obj_TorchExt();
                            case 6 -> new Obj_Potion();
                            case 7 -> new Obj_Colon();
                            default -> null;
                        };
                        obj.x = col;
                        obj.y = row;
                        loadedObjects.add(obj);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedObjects.toArray(new SuperObject[0]);
    }
    public void loadMapIntoTileNum(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (int row = 0; row < maxScreenRow; row++) {
                String line = br.readLine();
                if (line == null) {
                    System.out.println("Insufficient rows in map file: " + filePath);
                    break;
                }
                String[] numbers = line.split(" ");
                for (int col = 0; col < Math.min(maxScreenColumn, numbers.length); col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public SuperObject[] loadFromMapTile() {
        ArrayList<SuperObject> loadedObjects = new ArrayList<>();
        for (int row = 0; row < maxScreenRow; row++) {
            for (int col = 0; col < maxScreenColumn; col++) {
                int value = mapTileNum[col][row];
                if (value >= 50) {
                    SuperObject obj = switch (value-50) {
                        case 0 -> new Obj_Chest();
                        case 1 -> new Obj_BoxSmall();
                        case 2 -> new Obj_BoxBig();
                        case 3 -> new Obj_Ladder();
                        case 4 -> new Obj_Skull();
                        case 5 -> new Obj_TorchExt();
                        case 6 -> new Obj_Potion();
                        case 7 -> new Obj_Colon();
                        default -> null;
                    };
                    obj.x = col;
                    obj.y = row;
                    tiles[value] = obj;
                    loadedObjects.add(obj);
                }
            }
        }
        return loadedObjects.toArray(new SuperObject[0]);
    }
    public void drawTile(Graphics2D g2) {
        for (int row = 0; row < maxScreenRow; row++) {
            for (int col = 0; col < maxScreenColumn; col++) {
                int tileNum = mapTileNum[col][row];
                if (tileNum < tiles.length && tiles[tileNum] != null) {
                    if (tileNum < 50) {
                        g2.drawImage(tiles[tileNum].image, col * tileSize, row * tileSize, this.tileSize, this.tileSize, null);
                    }

                }
            }//chng
        }
    }

    public void addSuperObjectsToTiles() {

    }

}
