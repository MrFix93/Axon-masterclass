package nl.infosupport.demo.game.models.strategies;

import nl.infosupport.demo.game.models.Square;
import nl.infosupport.demo.game.models.utils.Squares;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AcrossMovingStrategyTest {

    @Test
    void movingAcrossTest_unlimited() {
        final AcrossMovingStrategy acrossMovingStrategy = new AcrossMovingStrategy();

        final List<Square> actualRange = acrossMovingStrategy.getRange(Squares.from("d4"));
        final List<Square> expectedRange = Squares.asList("a1", "b2", "c3", "e5", "f6", "g7", "h8", "e3", "f2", "g1", "c5", "b6", "a7");
        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void movingAcrossTest_limited() {
        final AcrossMovingStrategy acrossMovingStrategy = new AcrossMovingStrategy(MovingStrategy.RangeMode.SINGLE);

        final List<Square> actualRange = acrossMovingStrategy.getRange(Squares.from("d4"));
        final List<Square> expectedRange = Squares.asList("c3", "e5", "e3", "c5");

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }
}
