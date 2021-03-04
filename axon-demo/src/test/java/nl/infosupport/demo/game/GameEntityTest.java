package nl.infosupport.demo.game;

import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.BoardCreator;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.pieces.Bishop;
import nl.infosupport.demo.game.models.pieces.King;
import nl.infosupport.demo.game.models.pieces.Pawn;
import nl.infosupport.demo.game.models.pieces.Queen;
import nl.infosupport.demo.game.models.rules.CheckMateRule;
import nl.infosupport.demo.game.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.printer.FancyBoardPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameEntityTest {

    private final List<Move> scholarsMateMoves = List.of(
            new Move(Pawn.white(), "e2", "e4"),
            new Move(Pawn.black(), "e7", "e6"),
            new Move(Bishop.white(), "f1", "c4"),
            new Move(Pawn.black(), "e6", "e5"),
            new Move(Queen.white(), "d1", "h5"),
            new Move(Pawn.black(), "d7", "d6"),
            new Move(Queen.white(), "h5", "f7")
    );

    private final ConsoleBoardPrinter printer = new FancyBoardPrinter(System.out);

    @Test
    void testCompleteMatch_doesNotThrowExceptions() {
        final Board board = BoardCreator.fullBoard();

        Assertions.assertDoesNotThrow(() -> {
                    for (Move m : scholarsMateMoves) {
                        board.make(m);
                        board.commit(m);
                        printer.print(board);
                    }
                }
        );
    }

    @Test
    void testCompleteMatch_shouldNotAllowKingToCapture() throws IllegalChessMoveException {
        final Board board = BoardCreator.fullBoard();

        for (Move m : scholarsMateMoves) {
            board.make(m);
            board.commit(m);
        }

        Assertions.assertThrows(IllegalChessMoveException.class, () ->
                board.make(new Move(King.black(), "e8", "f7"))
        );
    }

    @Test
    void testCompleteMatch_shouldNotAllowKingToMove() throws IllegalChessMoveException {
        final Board board = BoardCreator.fullBoard();

        for (Move m : scholarsMateMoves) {
            board.make(m);
            board.commit(m);
        }

        Assertions.assertThrows(IllegalChessMoveException.class, () ->
                board.make(new Move(King.black(), "e8", "e7"))
        );
    }

    @Test
    void testCompleteMatch_isCheckMate() throws IllegalChessMoveException {
        final Board board = BoardCreator.fullBoard();

        for (Move m : scholarsMateMoves) {
            board.make(m);
            board.commit(m);
        }

        assertThat(CheckMateRule.isCheckMate(board, ChessColor.BLACK)).isTrue();
    }
}
