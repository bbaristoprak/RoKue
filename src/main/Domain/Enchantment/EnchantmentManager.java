package main.Domain.Enchantment;

import main.Domain.Game;

import java.util.ArrayList;
import java.util.Random;

public class EnchantmentManager {
    Game game;

    public EnchantmentManager(Game game) {
        this.game = game;
    }
    /**
     * Adds a random enchantment to the provided enchantment list.
     *
     * Requires:
     *  - `enchantmentList` must be initialized and ready for modification.
     *  - The `game` object must contain the `randomPlace()` method returning coordinates for placement.
     *  - The `game` object must have the `tileSize` property for correct position calculation.
     *
     * Modifies:
     *  - `enchantmentList` by adding a randomly selected enchantment.
     *
     * Effects:
     *  - A random enchantment is chosen (of types `Reveal`, `Luring Gem`, `BoostBoots`, `ExtraLife`, `ExtraTime`, or `CloakOfProtection`).
     *  - The enchantment’s position is set using the coordinates returned by `game.randomPlace()`.
     *  - The enchantment is added to `enchantmentList`.
     */
    public void addEnchantmentToArray(ArrayList<Enchantment> enchantmentList, Game game) {
        Random random = new Random();
        int rand = random.nextInt(7) ; // Generates random number between 1 and 6
        //int rand = 4;
        int[] arr = game.randomPlace();
        System.out.println(arr[0] + " " + arr[1]);

        int[] position = {arr[0] * game.tileSize, arr[1] * game.tileSize};

        if (rand == 0) {
            enchantmentList.add(new LuringGem(game, position));
        } else if (rand == 1) {
            enchantmentList.add(new BoostBoots(game, position));
        } else if (rand == 2) {
            enchantmentList.add(new ExtraLife(game, position));//test
        } else if (rand == 3) {
            enchantmentList.add(new ExtraTime(game, position));//test
        } else if (rand == 4) {
            enchantmentList.add(new CloakOfProtection(game, position));
        } else if (rand == 6) {
            enchantmentList.add(new Reveal(game, position));

        }
    }
}