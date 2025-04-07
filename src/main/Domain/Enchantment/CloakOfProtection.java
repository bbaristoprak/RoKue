package main.Domain.Enchantment;

import main.Domain.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class CloakOfProtection extends Enchantment implements BagEnchantment{
    public CloakOfProtection(Game game, int[] coordinates) {
        super(game, coordinates);
    }

    @Override
    public void onClick(Game game) {
        System.out.println("cloak of protection clicked");
        game.player.bag.addCloakOfProtection(this);
    }

    @Override
    public void getEnchantmentImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/enchantment/cloak-of-protection.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "Cloak Of Protection";
    }
}
