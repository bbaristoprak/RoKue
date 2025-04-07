package main.Domain.Enchantment;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class LuringGem extends Enchantment implements BagEnchantment{
    public LuringGem(Game game, int[] coordinates) {
        super(game, coordinates);
    }

    @Override
    public void onClick(Game game) {
        game.player.bag.addLuringGem(this);

    }

    @Override
    public void getEnchantmentImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/enchantment/luring-gem.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "Luring Gem";
    }

    public void useGem(){
        game.gemThrown = true;
        int thrownX = game.player.getX();
        int thrownY = game.player.getY();
        switch (game.gemThrownLoc) {
            case "W": // Thrown upwards
                thrownY = game.player.getY() - game.tileSize;
                break;
            case "A": // Thrown to the left
                thrownX = game.player.getX() - game.tileSize;
                break;
            case "S": // Thrown downwards
                thrownY = game.player.getY() + game.tileSize;
                break;
            case "D": // Thrown to the right
                thrownX = game.player.getX() + game.tileSize;
                break;
            default:
                System.out.println("Invalid gemThrownLoc: " + game.gemThrownLoc);
                return;
        }
        game.gemX = thrownX;
        game.gemY = thrownY;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        //reduce to normal
                        game.gemThrown = false;
                    }
                }, 2000
        );
        game.bpressed = false;
        //game.gemThrown = false;
    }

}
