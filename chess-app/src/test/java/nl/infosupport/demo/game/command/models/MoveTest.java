package nl.infosupport.demo.game.command.models;

import nl.infosupport.demo.game.command.models.pieces.Bishop;
import nl.infosupport.demo.game.command.models.pieces.Knight;
import nl.infosupport.demo.game.command.models.pieces.Rook;
import nl.infosupport.demo.game.command.models.utils.Path;
import nl.infosupport.demo.game.command.models.utils.Squares;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveTest {

    public static List<Arguments> knightJumps() {
        return List.of(
                Arguments.of("d4", "c2"),
                Arguments.of("d4", "e2"),
                Arguments.of("d4", "b3"),
                Arguments.of("d4", "b5"),
                Arguments.of("d4", "c6"),
                Arguments.of("d4", "e6"),
                Arguments.of("d4", "f5"),
                Arguments.of("d4", "f3")
        );
    }

    @Test
    void testCrossMovePath_upRight() {
        final Move move = new Move(new Bishop(ChessColor.WHITE), Squares.from("a1"), Squares.from("h8"));

        List<Square> expectedPath = Squares.asList("a1", "b2", "c3", "d4", "e5", "f6", "g7", "h8");
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @Test
    void testCrossMovePath_upLeft() {
        final Move move = new Move(new Bishop(ChessColor.WHITE), Squares.from("h1"), Squares.from("a8"));

        List<Square> expectedPath = Squares.asList("h1", "g2", "f3", "e4", "d5", "c6", "b7", "a8");
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @Test
    void testCrossMovePath_downLeft() {
        final Move move = new Move(new Bishop(ChessColor.WHITE), Squares.from("h8"), Squares.from("a1"));

        List<Square> expectedPath = Squares.asList("a1", "b2", "c3", "d4", "e5", "f6", "g7", "h8");
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @Test
    void testCrossMovePath_downRight() {
        final Move move = new Move(new Bishop(ChessColor.WHITE), Squares.from("a8"), Squares.from("h1"));

        List<Square> expectedPath = Squares.asList("h1", "g2", "f3", "e4", "d5", "c6", "b7", "a8");
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @Test
    void testHorizontalMove_right() {
        final Move move = new Move(new Rook(ChessColor.WHITE), Squares.from("a1"), Squares.from("a8"));

        List<Square> expectedPath = Squares.asList("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @Test
    void testHorizontalMove_down() {
        final Move move = new Move(new Rook(ChessColor.WHITE), Squares.from("a8"), Squares.from("a1"));

        List<Square> expectedPath = Squares.asList("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");

        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @Test
    void testVerticalMove() {
        final Move move = new Move(new Rook(ChessColor.WHITE), Squares.from("a1"), Squares.from("h1"));

        List<Square> expectedPath = Squares.asList("a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1");
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

    @ParameterizedTest
    @MethodSource(value = "knightJumps")
    void testKnightJumpMove(String startSquare, String endSquare) {
        final Move move = new Move(new Knight(ChessColor.WHITE), startSquare, endSquare);

        List<Square> expectedPath = Squares.asList(startSquare, endSquare);
        final List<Square> actualPath = Path.constructFrom(move);

        assertThat(actualPath).containsExactlyInAnyOrderElementsOf(expectedPath);
    }

}
