package main.Domain;

public interface GameListener {
    void onTimePercentageGroupUpdate(int num);
    void onRuneFound();
    void runeRelocated();
    void timeChanged(int newTime);
    void livesChanged(int newLives);
    void enchantmentAdded(String enchantmentName);
    void enchantmentRemoved(String enchantmentName);

}
