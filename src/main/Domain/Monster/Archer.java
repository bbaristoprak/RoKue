package main.Domain.Monster;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Archer extends Monster{
    public int id;
    public int actionLockCounter = 0;
    public int radius = 200;

    public Archer(Game game,int[] coordinates) {
        super(game);
        this.speed = 0;
        this.X = coordinates[0];
        this.Y = coordinates[1];
        this.id = 2;

    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 240) {
            System.out.println("Archer tried to shoot");
            if(playerDistance() <= radius && !game.player.hasCloakOfProtection){
                game.player.takeDamage();
            }

            actionLockCounter = 0;
        }

    }

    @Override
    public void getMonsterImage() {
        try {
            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/archer_right.png")));
            //TODO: add mirrored here
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return 2;
    }

    public int playerDistance(){
        if (game.player != null) {
            int playerX = game.player.X;
            int playerY = game.player.Y;


            return (int) Math.sqrt(Math.pow(playerX - this.X, 2) + Math.pow(playerY - this.Y, 2));
        }
        return -1;
    }

}