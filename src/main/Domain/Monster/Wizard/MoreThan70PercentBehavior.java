package main.Domain.Monster.Wizard;

import javax.swing.*;
import java.util.Random;

public class MoreThan70PercentBehavior implements WizardBehavior {
    @Override
    public Timer execute(Wizard wizard) {
        // Change the location of the rune every 3 seconds
        Timer timer = new Timer(3000, e -> {
            int i;
            for (i = 0; i < wizard.game.objects.size(); i++) {
                if (wizard.game.objects.get(i).hasRune) {
                    wizard.game.objects.get(i).setHasRune(false);
                    break;
                }
            }
            while (true) {
                Random rand = new Random();
                int j = rand.nextInt(wizard.game.objects.size());
                if (i != j) {
                    wizard.game.objects.get(j).setHasRune(true);
                    wizard.game.objectWithRune = wizard.game.objects.get(j);
                    break;
                }
            }
            wizard.game.notifyRuneLocationChanged();
        });
        timer.setRepeats(true);
        timer.start();
        return timer;
    }
}
