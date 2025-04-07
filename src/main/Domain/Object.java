package main.Domain;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Object {
    public int X;
    public int Y;
    public int speed;
    public String direction;
    public BufferedImage left, right;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public int invincibleTimer;
    public int cloakOfProtectionTimer;
    public int type; // 1 = player , 2 = monster
    public Game game;

    public Object(Game game){
        this.game = game;
    }

    public void setAction(){
    }

    public abstract void update();
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }
}
