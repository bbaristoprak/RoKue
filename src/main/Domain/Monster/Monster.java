package main.Domain.Monster;

import main.Domain.Game;
import main.Domain.Object;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Monster extends Object{

    public int id;
    public final String drawDirection;


    public Monster(Game game) {
        super(game);
        this.direction = "right";
        this.speed = 0;
        this.solidArea = new Rectangle(8, 16, 32, 32);
        this.drawDirection = "right";


        getMonsterImage();

    }



    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        if (drawDirection.equals("right")) image = right;
        else if (drawDirection.equals("left")) image = left;
        graphics2D.drawImage(image, getX(), getY(), super.game.tileSize, super.game.tileSize, null);
    }

    public void update(){
//        setAction();
        collisionOn = false;
        game.collisionHandler.checkTile(this);
        game.collisionHandler.checkCollision(this);

        if (!collisionOn) {
            switch (direction) {
                case "up":
                    setY(getY() - getSpeed());
                    break;
                case "down":
                    setY(getY() + getSpeed());
                    break;
                case "left":
                    setX(getX() - getSpeed());
                    break;
                case "right":
                    setX(getX() + getSpeed());
                    break;
            }
        }
        setAction();


    }

    public abstract void getMonsterImage(); // get from getPlayerImage
    // maybe implemented in object class


    public int getId() {
        return -1;
    }

    public int getType(){
        return 2;
    }


}