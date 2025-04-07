package main.Domain.Monster.Wizard;

import javax.swing.*;

public class LessThan30PercentBehavior implements WizardBehavior {
    @Override
    public Timer execute(Wizard wizard) {
        // Change the location of the player to a random empty location and disappear
        Timer timer = new Timer(1000, e -> {
            int[] arr = wizard.game.randomPlace();
            int newXTile = arr[0];
            int newYTile = arr[1];
            wizard.game.movePlayerTo(newXTile, newYTile);
            wizard.disappear();
        });
        timer.setRepeats(false);
        timer.start();
        return timer;
    }
}