package nl.infosupport.demo.game.models.strategies;

import nl.infosupport.demo.game.models.Square;
import nl.infosupport.demo.game.models.utils.Squares;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KnightJumpMovingStrategyTest {

    @Test
    void fullRange() {
        final KnightJumpMovingStrategy knightJumpMovingStrategy = new KnightJumpMovingStrategy();

        final List<Square> expectedRange = Squares.asList("c2", "e2", "b3", "b5", "c6", "e6", "f5", "f3");
        final List<Square> actualRange = knightJumpMovingStrategy.getRange(Squares.from("d4"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void limitedRange() {
        final KnightJumpMovingStrategy knightJumpMovingStrategy = new KnightJumpMovingStrategy();

        final List<Square> expectedRange = Squares.asList("c2", "b3");
        final List<Square> actualRange = knightJumpMovingStrategy.getRange(Squares.from("a1"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

    @Test
    void halfRange() {
        final KnightJumpMovingStrategy knightJumpMovingStrategy = new KnightJumpMovingStrategy();

        final List<Square> expectedRange = Squares.asList("a2", "e2", "b3", "d3");
        final List<Square> actualRange = knightJumpMovingStrategy.getRange(Squares.from("c1"));

        assertThat(actualRange).containsExactlyInAnyOrderElementsOf(expectedRange);
    }

}
