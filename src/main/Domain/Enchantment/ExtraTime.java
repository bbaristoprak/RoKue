package main.Domain.Enchantment;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ExtraTime extends  Enchantment{
    public ExtraTime(Game game, int[] coordinates){
        super(game, coordinates);
    }

    @Override
    public void onClick(Game game) {
        System.out.println("extra time clicked");
        game.remainingTime += 6; // for better look in panel
    }

    @Override
    public void getEnchantmentImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/enchantment/extra_time.jpeg")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
