package main.Domain.Monster.Wizard;

import javax.swing.*;

public class Between30And70PercentBehavior implements WizardBehavior {
    @Override
    public Timer execute(Wizard wizard) {
        // Stay in place and disappear after 2 seconds
        Timer timer = new Timer(2000, e -> wizard.disappear());
        timer.setRepeats(false);
        timer.start();
        return timer;
    }
}