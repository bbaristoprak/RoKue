package main.Domain.Enchantment;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ExtraLife extends Enchantment{
    public ExtraLife(Game game, int[] coordinates) {
        super(game, coordinates);
    }

    @Override
    public void onClick(Game game) {
        System.out.println("extra life clicked");
        if(game.player.lives < 4) {
            game.player.lives += 1;
        }// for better look in panel
        game.notifyLivesChange();
    }

    @Override
    public void getEnchantmentImage() {
        try { //has to be changed
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/map/heart.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}