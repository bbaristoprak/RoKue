package main.Domain;

import main.Domain.HallObject.*;


public class PlaceObjectHandler {
    BuildHall hall;
    int objectCount = 0;

    public PlaceObjectHandler(BuildHall hall) {
        this.hall = hall;
    }


public void putObject(int x, int y, int type) {
    if (isTileEmpty(x, y)) {
        SuperObject obj = createObject(type, x, y);
        if (obj != null) {
            hall.objects[objectCount++] = obj; //put each object into the array
            hall.tileHandler.mapTileNum[x][y] = 50 + type; //to make them different from walls
            hall.tileHandler.tiles[50 + type] = obj;
            System.out.println("Object placed at: " + x + ", " + y + " with type: " + type);
        }
    } else {
        System.out.println("Tile is not empty: " + x + ", " + y);
    }
}
    public SuperObject createObject(int type, int x, int y) {
        SuperObject obj = switch (type) {
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
        if (obj != null) {
            obj.setX(x);
            obj.setY(y);
        }
        return obj;
    }

    private boolean isTileEmpty(int x, int y) {
        return hall.tileHandler.mapTileNum[x][y] == 0;
    }

}
