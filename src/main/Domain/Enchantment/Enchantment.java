package main.Domain.Enchantment;

import main.Domain.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enchantment {
    protected int x, y;
    protected Game game;
    protected BufferedImage image;
    protected long despawnTimer;

    public Enchantment(Game game, int[] coordinates){
        this.game = game;
        this.x = coordinates[0];
        this.y = coordinates[1];

        getEnchantmentImage();
    }

    public int getX(){ return x;}
    public int getY(){ return  y;}
    public void setX(int newX){
        this.x = newX;
    }
    public void setY(int newY){
        this.y = newY;
    }

    public abstract void onClick(Game game);
    public abstract void getEnchantmentImage();

    public void draw(Graphics2D graphics2D){
        graphics2D.drawImage(image, x, y, game.tileSize, game.tileSize, null);
    }
}
