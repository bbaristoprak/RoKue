package main.Domain.HallObject;

import main.Domain.Tile.Tile;

public class ObjectHandler {
    int maxScreenRow;
    int maxScreenColumn;
    int tileSize;
    public Tile[] tiles;
    public int[][] mapTileNum;

    public ObjectHandler(int maxScreenColumn, int maxScreenRow, int tileSize) {
        this.maxScreenColumn = maxScreenColumn;
        this.maxScreenRow = maxScreenRow;
        this.tileSize = tileSize;
        this.tiles = new Tile[100];
        this.mapTileNum = new int[maxScreenColumn][maxScreenRow];
    }

    public void setMapTileNum(int[][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }
}
