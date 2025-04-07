package main.Domain.Monster;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Fighter extends Monster{

    public int actionLockCounter;
    public int attackLockCounter;
    public int gemLockCounter;
    public String drawDirection = "right";
    public int id;


    public Fighter(Game game,int[] coordinates) {
        super(game);
        this.speed = 5;
        this.X = coordinates[0];
        this.Y = coordinates[1];
        this.id = 1;
    }

    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        if (drawDirection.equals("right")) image = right;
        else if (drawDirection.equals("left")) image = left;
        graphics2D.drawImage(image, getX(), getY(), super.game.tileSize, super.game.tileSize, null);
    }

    public void setAction(){

        attackLockCounter++;

        if(game.collisionHandler.checkCollision(this) &&  60 < attackLockCounter) {
            game.player.takeDamage();
            attackLockCounter = 0;
        }

        actionLockCounter++;

        if(game.gemThrown){//game.gemThrown
            // Move towards the gem's location
            int goX = game.gemX;
            int goY = game.gemY;
            gemLockCounter++;

            if (Objects.equals(game.gemThrownLoc, "D") && gemLockCounter < 120 ) {//&& game.collisionHandler.checkTile();
                direction = "right";
                drawDirection = "right";
                gemLockCounter = 0;
            } else if (Objects.equals(game.gemThrownLoc, "A") && gemLockCounter< 120) {
                direction = "left";
                drawDirection = "left";
                gemLockCounter = 0;
            } else if (Objects.equals(game.gemThrownLoc, "S") && gemLockCounter< 120) {
                direction = "down";
                gemLockCounter = 0;
            } else if (Objects.equals(game.gemThrownLoc, "W") && gemLockCounter< 120) {
                direction = "up";
                gemLockCounter = 0;
            }

            // Stop moving if the monster reaches the gem's location
//            if (Math.abs(X - goX) <= speed && Math.abs(Y - goY) <= speed) {
//                game.gemThrown = false; // Reset the gemThrown flag
//                System.out.println("Monster reached the Luring Gem at: " + goX + ", " + goY);
//            }

        }

        if(actionLockCounter == 120 && !game.gemThrown){//120 idi
            Random rand = new Random();
            int i = rand.nextInt(100) + 1;

            if(i <= 25){
                direction = "right";
                drawDirection = "right";

            }
            if(i > 25 && i <= 50){
                direction = "left";
                drawDirection = "left";
            }
            if(i > 50 && i <= 75){
                direction = "up";
            }
            if(i > 75 && i <= 100){
                direction = "down";
            }
            actionLockCounter = 0;
        }

    }
    @Override
    public void getMonsterImage() {

        try {
            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/fighter_right.png")));
            left = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/fighter_left.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return 1;
    }
}