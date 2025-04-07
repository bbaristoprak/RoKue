package main.Domain.HallObject;

import main.Domain.Tile.Tile;

import java.awt.*;
import java.util.ArrayList;

public class SuperObject extends Tile implements Comparable<SuperObject>{
    public String name;
    public int x, y;
    public static ArrayList<SuperObject> superObjects = new ArrayList<>();
    public boolean hasRune = false;

    public SuperObject(){
        this.collision = true;
        superObjects.add(this);
    }
    public void draw(Graphics2D g2, int tileSize) {
        if (image != null) {
            g2.drawImage(image, x * tileSize, y * tileSize, tileSize, tileSize, null);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHasRune(boolean b){this.hasRune = b;}

    @Override
    public int compareTo(SuperObject superObject) {
        return Integer.compare(this.y, superObject.y);
    }
}