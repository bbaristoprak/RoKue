package main.Domain;

import main.Domain.Monster.Monster;

import java.util.ArrayList;

public class CollisionHandler {
    Game game;
    public CollisionHandler(Game game) {
        this.game = game;
    }

    public void checkTile(Object o) {
        int objectLeftX = o.getX() + o.solidArea.x;
        int objectRightX = o.getX() + o.solidArea.x + o.solidArea.width;
        int objectTopY = o.getY() + o.solidArea.y;
        int objectBotY = o.getY() + o.solidArea.y + o.solidArea.height;

        int objectLeftCol = objectLeftX/game.tileSize;
        int objectRightCol = objectRightX/game.tileSize;
        int objectTopRow = objectTopY/game.tileSize;
        int objectBotRow = objectBotY/game.tileSize;

        int tileNum1, tileNum2;
        switch (o.getDirection()) {
            case "up":
                objectTopRow = (objectTopY - o.getSpeed()) / game.tileSize;
                tileNum1 = game.tileHandler.mapTileNum[objectLeftCol][objectTopRow];
                tileNum2 = game.tileHandler.mapTileNum[objectRightCol][objectTopRow];
                if (game.tileHandler.tiles[tileNum1].collision || game.tileHandler.tiles[tileNum2].collision) {
                    o.collisionOn = true;
                }
                break;
            case "down":
                objectBotRow = (objectBotY + o.getSpeed()) / game.tileSize;
                tileNum1 = game.tileHandler.mapTileNum[objectLeftCol][objectBotRow];
                tileNum2 = game.tileHandler.mapTileNum[objectRightCol][objectBotRow];
                if (game.tileHandler.tiles[tileNum1].collision || game.tileHandler.tiles[tileNum2].collision) {
                    o.collisionOn = true;
                }
                break;
            case "left":
                objectLeftCol = (objectLeftX - o.getSpeed()) / game.tileSize;
                tileNum1 = game.tileHandler.mapTileNum[objectLeftCol][objectTopRow];
                tileNum2 = game.tileHandler.mapTileNum[objectLeftCol][objectBotRow];
                if (game.tileHandler.tiles[tileNum1].collision || game.tileHandler.tiles[tileNum2].collision) {
                    o.collisionOn = true;
                }
                break;
            case "right":
                objectRightCol = (objectRightX + o.getSpeed()) / game.tileSize;
                tileNum1 = game.tileHandler.mapTileNum[objectRightCol][objectTopRow];
                tileNum2 = game.tileHandler.mapTileNum[objectRightCol][objectBotRow];
                if (game.tileHandler.tiles[tileNum1].collision || game.tileHandler.tiles[tileNum2].collision) {
                    o.collisionOn = true;
                }
                break;
        }

    }

    public int checkCollision(Object o, ArrayList<Monster> target) {
        int index = 999; // Default value when no collision occurs

        // Calculate the object's solid area boundaries
        int objectLeftX = o.getX() + o.solidArea.x;
        int objectRightX = o.getX() + o.solidArea.x + o.solidArea.width;
        int objectTopY = o.getY() + o.solidArea.y;
        int objectBotY = o.getY() + o.solidArea.y + o.solidArea.height;

        for (int i = 0; i < target.size(); i++) {
            Monster monster = target.get(i);

            if (monster != null) {

                int monsterLeftX = monster.getX() + monster.solidArea.x;
                int monsterRightX = monster.getX() + monster.solidArea.x + monster.solidArea.width;
                int monsterTopY = monster.getY() + monster.solidArea.y;
                int monsterBotY = monster.getY() + monster.solidArea.y + monster.solidArea.height;


                switch (o.getDirection()) {
                    case "up":
                        if (checkIntersection(
                                objectLeftX, objectRightX,
                                objectTopY - o.getSpeed(), objectBotY - o.getSpeed(),
                                monsterLeftX, monsterRightX,
                                monsterTopY, monsterBotY)) {
                            o.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        if (checkIntersection(
                                objectLeftX, objectRightX,
                                objectTopY + o.getSpeed(), objectBotY + o.getSpeed(),
                                monsterLeftX, monsterRightX,
                                monsterTopY, monsterBotY)) {
                            o.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        if (checkIntersection(
                                objectLeftX - o.getSpeed(), objectRightX - o.getSpeed(),
                                objectTopY, objectBotY,
                                monsterLeftX, monsterRightX,
                                monsterTopY, monsterBotY)) {
                            o.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        if (checkIntersection(
                                objectLeftX + o.getSpeed(), objectRightX + o.getSpeed(),
                                objectTopY, objectBotY,
                                monsterLeftX, monsterRightX,
                                monsterTopY, monsterBotY)) {
                            o.collisionOn = true;
                            index = i;
                        }
                        break;
                }
            }
        }

        return index;
    }

    private boolean checkIntersection(int objLeftX, int objRightX, int objTopY, int objBotY,
                                      int targetLeftX, int targetRightX, int targetTopY, int targetBotY) {
        return objRightX > targetLeftX &&
                objLeftX < targetRightX &&
                objBotY > targetTopY &&
                objTopY < targetBotY;
    }

    public boolean checkCollision(Object o) {

        boolean contactPlayer = false;

        o.solidArea.x = o.X + o.solidArea.x;
        o.solidArea.y = o.Y + o.solidArea.y;

        game.player.solidArea.x = game.player.X + game.player.solidArea.x;
        game.player.solidArea.y = game.player.Y + game.player.solidArea.y;

        switch (o.getDirection()) {
            case "up":
                o.solidArea.y -= o.getSpeed();
                break;
            case "down":
                o.solidArea.y += o.getSpeed();
                break;

            case "left":
                o.solidArea.x -= o.getSpeed();
                break;

            case "right":
                o.solidArea.x += o.getSpeed();

                break;
        }
        if (o.solidArea.intersects(game.player.solidArea)) {
            o.collisionOn = true;
            contactPlayer = true;
        }

        o.solidArea.x = 8;
        o.solidArea.y = 4;
        game.player.solidArea.x = 8;
        game.player.solidArea.y = 4;

        return contactPlayer;

    }

}
