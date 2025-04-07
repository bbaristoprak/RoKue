package main.Domain.Enchantment;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BoostBoots extends Enchantment{
    private static final int BOOST_AMOUNT = 5; // Speed boost amount
    private static final int BOOST_DURATION = 5000; // Duration in milliseconds (5 seconds)

    public BoostBoots(Game game, int[] coordinates) {
        super(game, coordinates);
    }

    @Override
    public void onClick(Game game) {
        System.out.println("boost boots clicked");
        game.player.speed += 5;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        //reduce to normal
                        game.player.speed -= 5;
                    }
                },
                5000
        );// for better look in panel
    }

    @Override
    public void getEnchantmentImage() {
        try { //has to be changed
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/enchantment/img.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }


}