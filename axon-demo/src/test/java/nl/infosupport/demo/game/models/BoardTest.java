package nl.infosupport.demo.game.models;

import nl.infosupport.demo.game.exceptions.IllegalBoardSquareException;
import nl.infosupport.demo.game.models.pieces.*;
import nl.infosupport.demo.game.utils.Squares;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void testIsCheck_simplePawnKing() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(new Square(File.a, Rank.one), new Pawn(ChessColor.WHITE))
                .with(new Square(File.b, 2), new King(ChessColor.BLACK))
                .build();

        assertThat(board.isCheck(ChessColor.BLACK)).isTrue();
    }

    @Test
    void testIsCheck_oneWayOut() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(Squares.from("d1"), King.white())
                .with(Squares.from("d3"), Queen.black())
                .with(Squares.from("h6"), Bishop.black())
                .build();

        assertThat(board.isCheck(ChessColor.WHITE)).isTrue();
        assertThat(board.isCheckMate(ChessColor.WHITE)).isFalse();
    }

    @Test
    void testIsCheck_pieceBlocksAttackOnKing() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(Squares.from("d1"), Queen.white())
                .with(Squares.from("e2"), Knight.white())
                .with(Squares.from("f3"), King.white())
                .build();

        assertThat(board.isCheck(ChessColor.BLACK)).isFalse();
    }

    @Test
    void testCheckmate_kingCanCaptureAttacker() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(Squares.from("h1"), King.white())
                .with(Squares.from("g1"), Queen.black())
                .build();

        assertThat(board.isCheck(ChessColor.WHITE)).isTrue();
        assertThat(board.isCheckMate(ChessColor.WHITE)).isFalse();
    }

    @Test
    void testIsCheckmate_noWayOut() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(Squares.from("d1"), King.white())
                .with(Squares.from("d3"), Queen.black())
                .with(Squares.from("h6"), Bishop.black())
                .with(Squares.from("f3"), Knight.black())
                .build();

        assertThat(board.isCheck(ChessColor.WHITE)).isTrue();
        assertThat(board.isCheckMate(ChessColor.WHITE)).isTrue();
    }

    @Test
    void testIsCheckmate_attackAttackerToAvoidCheckmate() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(Squares.from("d1"), King.white())
                .with(Squares.from("c2"), Pawn.white())
                .with(Squares.from("d3"), Queen.black())
                .with(Squares.from("h6"), Bishop.black())
                .with(Squares.from("f3"), Knight.black())
                .build();

        assertThat(board.isCheck(ChessColor.WHITE)).isTrue();
        assertThat(board.isCheckMate(ChessColor.WHITE)).isFalse();
    }

    @Test
    void testGetPieceBySquare_valid() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(new Square(File.a, Rank.one), new Pawn(ChessColor.WHITE))
                .build();

        assertThat(board.getPieceBySquare(Squares.from("a1"))).isPresent();
        assertThat(board.getPieceBySquare(Squares.from("a1")).get()).isEqualTo(Pawn.white());
    }

    @Test
    void testPieceIsAtSquare_invalid() throws IllegalBoardSquareException {
        final Board board = new BoardCreator.Builder()
                .with(new Square(File.h, Rank.seven), new Pawn(ChessColor.WHITE))
                .build();

        assertThat(board.getPieceBySquare(Squares.from("a2"))).isEmpty();
    }
}
