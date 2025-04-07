package main.Domain.Monster.Wizard;

import main.Domain.Game;
import main.Domain.GameListener;
import main.Domain.Monster.Monster;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class Wizard extends Monster implements GameListener {
    private WizardBehavior behavior;
    public int id;
    Timer behaviorTimer;
    public Wizard(Game game, int[] coordinates) {
        super(game);
        this.speed = 0;
        this.X = coordinates[0];
        this.Y = coordinates[1];
        this.id = 3;
        performBehavior(game.remainingTimePercentageGroup);
        game.addGameListener(this);
    }
    @Override

    public void getMonsterImage() {
        try {
            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/wizard_right.png")));
            //TODO: add mirrored here
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void updateBehavior(int num) {
        switch (num) {
            case 0:
                behavior = new LessThan30PercentBehavior();
                break;
            case 1:
                behavior = new Between30And70PercentBehavior();
                break;
            case 2:
                behavior = new MoreThan70PercentBehavior();
                break;
        }
        stopBehaviorTimer();
    }

    public void performBehavior(int num) {
        updateBehavior(num);
        behaviorTimer = behavior.execute(this);
    }
    public void stopBehaviorTimer() {
        if (behaviorTimer != null) {
            behaviorTimer.stop();
        }
    }
    public void disappear() {
        stopBehaviorTimer();
        game.monsterList.remove(this);
        game.removeGameListener(this);
    }
    public int getId() {
        return 3;
    }

    @Override
    public void onTimePercentageGroupUpdate(int num) {
        performBehavior(num);
    }

    @Override
    public void onRuneFound() {

    }

    @Override
    public void runeRelocated() {

    }

    @Override
    public void timeChanged(int newTime) {

    }

    @Override
    public void livesChanged(int newLives) {

    }

    @Override
    public void enchantmentAdded(String enchantmentName) {

    }

    @Override
    public void enchantmentRemoved(String enchantmentName) {

    }
}