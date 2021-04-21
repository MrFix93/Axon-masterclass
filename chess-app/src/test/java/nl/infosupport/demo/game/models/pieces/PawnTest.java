package nl.infosupport.demo.game.models.pieces;

import nl.infosupport.demo.game.models.File;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.Rank;
import nl.infosupport.demo.game.models.Square;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    @Test
    void testNormalMove_valid() {
        final Piece whitePaw = Pawn.white();
        final Square startSquare = Square.of(File.a, Rank.one);
        final Square forwardSquare = Square.of(File.a, Rank.two);

        assertThat(whitePaw.getRange(startSquare)).containsExactly(forwardSquare);
    }

    @Test
    void testNormalMove_valid_start() {
        final Piece whitePaw = Pawn.white();
        final Square startSquare = Square.of(File.a, Rank.two);
        final Square forwardSquare = Square.of(File.a, Rank.three);
        final Square doubleForwardSquare = Square.of(File.a, Rank.four);

        assertThat(whitePaw.getRange(startSquare)).containsExactlyInAnyOrder(forwardSquare, doubleForwardSquare);
    }

    @Test
    void testNormalMove_valid_attack() {
        final Piece whitePaw = Pawn.white();
        final Square startSquare = Square.of(File.b, Rank.two);
        final Square attackSquareLeft = Square.of(File.a, Rank.three);
        final Square attackSquareRight = Square.of(File.c, Rank.three);

        assertThat(whitePaw.getAttackRange(startSquare)).containsExactlyInAnyOrder(attackSquareLeft, attackSquareRight);
    }

}
