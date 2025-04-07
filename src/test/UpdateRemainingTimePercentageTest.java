package test;

import static org.junit.jupiter.api.Assertions.*;

import main.Domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Requires:
 *totalTime is bigger than > 0.
 *remainingTime is bigger that or equals to >= 0.
 *remainingTimePercentageGroup must have one of the 0, 1, or 2 values.
 *Any listeners (our GameListener) must be already registered and has to be notified.
 *
 * Modifies:
 *The value of remainingTimePercentageGroup if the new percentage is from different category.
 *If the group changes triggers the notifyTimePercentageGroupUpdate() method.
 *
 * Effects:
 *(remainingTime / totalTime) * 100 = remaining time ratio , is computed.
 *Groups: (are specified)
 * 0 => <= 30%
 * 1 => 31% to 70%
 * 2 => > 70%
 *
 *remainingTimePercentageGroup, is updated and notified the listeners if the new computed group is different from the current.
 */

public class UpdateRemainingTimePercentageTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1, false);
        game.setTotalTime(100); //just an example of total time
    }

    @Test
    void testRemainingTimeBelow30Percent() {
        game.setRemainingTime(20); //20%*total time
        game.setRemainingTimePercentageGroup(2); //initial group is set to different from expected to test it

        game.updateRemainingTimePercentage();

        //the group is now 0 (<= 30%) assert it
        assertEquals(0, game.remainingTimePercentageGroup,
                "Expected group 0 when remaining time is below or equal to 30%");
    }

    @Test
    void testRemainingTimeBetween30And70Percent() {
        game.setRemainingTime(50); // 50%*total time
        game.setRemainingTimePercentageGroup(2); //initial group is set to different from expected to test it

        game.updateRemainingTimePercentage();

        //the group is now 1 (31%-70%) assert it
        assertEquals(1, game.remainingTimePercentageGroup,
                "Expected group 1 when remaining time is between 31% and 70%");
    }

    @Test
    void testRemainingTimeAbove70Percent() {
        game.setRemainingTime(80); // 80%*total time
        game.setRemainingTimePercentageGroup(0); //initial group is set to different from expected to test it

        game.updateRemainingTimePercentage();

        //the group is now 2 (> 70%) assert it
        assertEquals(2, game.remainingTimePercentageGroup,
                "Expected group 2 when remaining time is above 70%");
    }
}
