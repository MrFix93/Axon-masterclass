package nl.infosupport.demo.game.command.models.strategies;

import nl.infosupport.demo.game.command.models.Square;
import nl.infosupport.demo.game.command.utils.Squares;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorizontalMovingStrategyTest {

    @Test
    void testUnlimited_fullRange() {
        final HorizontalMovingStrategy strategy = new HorizontalMovingStrategy();

        final List<Square> expectedRange = Squares.asList("b4", "c4", "d4", "e4", "f4", "g4", "h4");
        final List<Square> actualRange = strategy.getRange(Squares.from("a4"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void testLimited_bothDirections() {
        final HorizontalMovingStrategy strategy = new HorizontalMovingStrategy(MovingStrategy.RangeMode.SINGLE);

        final List<Square> expectedRange = Squares.asList("a4", "c4");
        final List<Square> actualRange = strategy.getRange(Squares.from("b4"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }


}
