package nl.infosupport.demo.game.models.strategies;

import nl.infosupport.demo.game.models.Square;
import nl.infosupport.demo.game.utils.Squares;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VerticalMovingStrategyTest {

    @Test
    void testUnlimited_fullRange() {
        final VerticalMovingStrategy strategy = new VerticalMovingStrategy();
        final List<Square> expectedRange = Squares.asList("a2", "a3", "a4", "a5", "a6", "a7", "a8");

        final List<Square> actualRange = strategy.getRange(Squares.from("a1"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void testUnlimited_bothSides_fullRange() {
        final VerticalMovingStrategy strategy = new VerticalMovingStrategy();
        final List<Square> expectedRange = Squares.asList("a1", "a2", "a3", "a4", "a6", "a7", "a8");

        final List<Square> actualRange = strategy.getRange(Squares.from("a5"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void testLimited_limitedRange() {
        final VerticalMovingStrategy strategy = new VerticalMovingStrategy(MovingStrategy.RangeMode.SINGLE);
        final List<Square> expectedRange = Squares.asList("a2");

        final List<Square> actualRange = strategy.getRange(Squares.from("a1"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void testLimited_bothSides() {
        final VerticalMovingStrategy strategy = new VerticalMovingStrategy(MovingStrategy.RangeMode.SINGLE);
        final List<Square> expectedRange = Squares.asList("a1", "a3");

        final List<Square> actualRange = strategy.getRange(Squares.from("a2"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }
}
