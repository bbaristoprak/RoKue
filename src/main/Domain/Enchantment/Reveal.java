package main.Domain.Enchantment;

import main.Domain.Game;
import main.Domain.HallObject.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;



public class Reveal extends Enchantment implements BagEnchantment{
    public int rune_Y;
    public int rune_X;
    public boolean isRevealed = false;

    public Reveal(Game game, int[] coordinates) {
        super(game, coordinates);
    }

    @Override
    public void onClick(Game game) {
        game.player.bag.addReveal(this);
    }

    @Override
    public void getEnchantmentImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/enchantment/reveal.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void useReveal(){
        game.randomX();
        game.randomY();
        game.revealActive = true;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        //reduce to normal
                        game.revealActive = false;
                    }
                }, 10000
        );
    }

    @Override
    public String toString() {
        return "Reveal Gem";
    }
}