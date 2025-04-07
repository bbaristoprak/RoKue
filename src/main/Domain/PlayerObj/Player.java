package main.Domain.PlayerObj;

import main.Domain.Enchantment.LuringGem;
import main.Domain.KeyHandler;
import main.Domain.Game;
import main.Domain.Object;
import main.Domain.HallObject.SuperObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Player extends Object {
    public int lives;
    public int X;
    public int Y;
    public int speed;
    Game game;
    public KeyHandler keyHandler;
    public BufferedImage right1, right2, left1, left2, standingRight, standingLeft, cloakRight, cloakLeft;
    public String direction;
    public String drawDirection;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public boolean isMoving = false;
    public boolean hasCloakOfProtection = false;

    public Bag bag;
    public Timer revealTimer;

    public Player(Game game, KeyHandler keyHandler, boolean isLoaded){
        super(game);
        this.game = game;
        this.bag = new Bag(this);
        this.keyHandler = keyHandler;
        this.direction = "right";
        this.drawDirection = "right";
        if (!isLoaded) {
            int[] position = randomPlace();
            this.X = position[0]* game.tileSize;
            this.Y = position[1]* game.tileSize;
            this.lives = 3;
        }
        this.speed = 5;
        solidArea = new Rectangle(8, 4, 32, 40);
        getPlayerImage();
    }

    public void getPlayerImage(){
        try {
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/girlplayer4x_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/girlplayer4x_right_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/girlplayer4x_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/girlplayer4x_left_2.png")));
            standingRight = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/girl_player_mirrored.png")));
            standingLeft = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/girl_player_left.png")));
            cloakRight =ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/cloak-right-girl.png")));
            cloakLeft =ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/cloak-of-protection-girl.png")));
//            cloakRight =ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/cloak-right-boy.png")));
//            cloakLeft =ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/cloak-of-protection-boy.png")));
//            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player4x_right_1.png")));
//            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player4x_right_2.png")));
//            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player4x_left_1.png")));
//            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player4x_left_2.png")));
//            standingRight = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_right.png")));
//            standingLeft = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_left.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed) {
            isMoving = true;
            if (keyHandler.upPressed) {
                direction = "up";
                move();
            }
            if (keyHandler.downPressed ) {
                direction = "down";
                move();
            }
            if (keyHandler.leftPressed ) {
                drawDirection = "left";
                direction = "left";
                move();
            }
            if (keyHandler.rightPressed) {
                drawDirection = "right";
                direction = "right";
                move();
            }

        } else {

            isMoving = false;
        }

        spriteCounter++;
        if (spriteCounter > 6) {
            spriteNumber = spriteNumber == 1 ? 2 : 1;
            spriteCounter = 0;
        }

        if (invincible) {
            invincibleTimer++;
            if (invincibleTimer > 60) {
                invincible = false;
                invincibleTimer = 0;
            }
        }
        if (keyHandler.pPressed) {
            if (bag.numCloakOfProtection > 0) {
                //bag.numCloakOfProtection--;
                this.hasCloakOfProtection = true;
                bag.cloakOfProtectionUsed();
            }
        }

        if (hasCloakOfProtection) {
            cloakOfProtectionTimer++;
            if (cloakOfProtectionTimer > 1200) {
                this.hasCloakOfProtection = false;
            }
        }
        if (keyHandler.rPressed) {
            if (bag.getRevealCount() > 0 && !game.revealActive) {
                bag.getReveal().useReveal();
                bag.revealUsed();
            }
        }
        if (keyHandler.bPressed) {
            if (bag.getLuringGem() > 0 && !game.gemThrown) {//&& !game.gemThrown
                game.bpressed = true;
            }
        }
        if (keyHandler.wPressed) {
            if (game.bpressed) {
                game.gemThrownLoc = "W";
                bag.getLuring().useGem();
                bag.luringGemUsed();
            }
        }
        if (keyHandler.sPressed) {
            if (game.bpressed) {
                game.gemThrownLoc = "S";
                bag.getLuring().useGem();
                bag.luringGemUsed();
            }
        }
        if (keyHandler.dPressed) {
            if (game.bpressed) {
                game.gemThrownLoc = "D";
                bag.getLuring().useGem();
                bag.luringGemUsed();
            }
        }
        if (keyHandler.aPressed) {
            if (game.bpressed) {
                game.gemThrownLoc = "A";
                bag.getLuring().useGem();
                bag.luringGemUsed();
            }

        }
        if (keyHandler.cPressed) {
            if (game.gemThrown) {
                int[] loca = {0,0}; //idk about this
                bag.addLuringGem(new LuringGem(game, loca ));
                game.bpressed = false;
                game.gemThrown = false;
                game.gemThrownLoc = null;
            }

        }

    }

    private int[] calculateRandomValidLocation(int playerX, int playerY) {
        Random random = new Random();
        for (int attempt = 0; attempt < 10; attempt++) { // Limit attempts to prevent infinite loops
            int offsetX = random.nextInt(3) - 1; // Generate offsets [-1, 0, 1]
            int offsetY = random.nextInt(3) - 1;

            int targetX = playerX + offsetX;
            int targetY = playerY + offsetY;

            // Ensure target location is within bounds and not on a wall
            if (targetX > 0 && targetX < game.tileHandler.maxScreenColumn &&
                    targetY > 0 && targetY < game.tileHandler.maxScreenRow &&
                    game.tileHandler.mapTileNum[targetX][targetY] == 0) {
                return new int[]{targetX, targetY};
            }
        }
        System.out.println("Failed to find a valid throw location.");
        return null;
    }





    public void move() {
        collisionOn = false;
        game.collisionHandler.checkTile(this);
        int monsterIndex = game.collisionHandler.checkCollision(this, game.monsterList);
        monsterCollide(monsterIndex);

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
    }
    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        if (isMoving && !hasCloakOfProtection){
            if (drawDirection.equals("right")) {
                if (spriteNumber == 1) image = right1;
                if (spriteNumber == 2) image = right2;
            }
            else if (drawDirection.equals("left")) {
                if (spriteNumber == 1) image = left1;
                if (spriteNumber == 2) image = left2;
            }
        }
        else if (isMoving && hasCloakOfProtection){
            if (drawDirection.equals("right")) {
                image = cloakRight;
            }
            else if (drawDirection.equals("left")) {
                image = cloakLeft;
            }
        }
        else if (!isMoving && hasCloakOfProtection){
            if (drawDirection.equals("right")) {
                image = cloakRight;
            }
            else if (drawDirection.equals("left")) {
                image = cloakLeft;
            }
        }
        else {
            if (drawDirection.equals("right")) {
                image = standingRight;
            } else if (drawDirection.equals("left")) {
                image = standingLeft;
            }
        }
        graphics2D.drawImage(image, getX(), getY(), game.tileSize, game.tileSize, null);
    }


    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String getDirection() {
        return direction;
    }
    public void setX(int newX){
        X = newX;
    }
    public void setY(int newY){
        Y = newY;
    }
    public int getSpeed(){
        return speed;
    }

    public boolean isNear(SuperObject obj) {
        int dx = this.X - obj.x*48;
        int dy = this.Y - obj.y*48;
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance < 48.0;
    }

    public int[] randomPlace() {
        Random random = new Random();
        int randX = random.nextInt(3, 24);
        int randY = random.nextInt(3, 15);
        while (game.tileHandler.mapTileNum[randX][randY] != 0) {
            randX = random.nextInt(3, 24);
            randY = random.nextInt(3, 15);
        }

        return new int[]{randX, randY};
    }

    public void monsterCollide(int monsterIndex) {
        if (monsterIndex != 999) {
            if(game.monsterList.get(monsterIndex).getId() == 1 && !invincible){
                takeDamage();
                System.out.println("Lives: " + this.lives);
                invincible = true;
            }
        }

    }

    public void takeDamage() {
        this.lives--;
        game.notifyLivesChange();
    }

}