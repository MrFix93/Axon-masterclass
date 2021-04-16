package nl.infosupport.demo.game.command.models.rules;

import nl.infosupport.demo.game.command.models.*;
import nl.infosupport.demo.game.command.models.pieces.King;
import nl.infosupport.demo.game.command.utils.Moves;

import java.util.Map;
import java.util.Optional;

public class CheckMateRule {

    private CheckMateRule() {
        // Do not instantiate this class
    }

    public static boolean isCheckMate(Board board, ChessColor color) {
        final Map<Square, Piece> pieces = board.getPieceByColor(color);

        final Optional<Move> eligibleMove = pieces.entrySet().stream()
                .flatMap(position -> Moves.constructAllMoves(position.getValue(), position.getKey()).stream())
                .filter(board::isValidMove)
                .findFirst();

        return eligibleMove.isEmpty();
    }

    public static boolean isCheck(Board board, ChessColor color) {
        final Optional<Square> kingSquare = board.getPieceByTypeAndColor(color, King.class);

        if (kingSquare.isEmpty()) {
            return false;
        }

        final ChessColor oppositeColor = color.invert();
        final Map<Square, Piece> piecesFromOpponent = board.getPieceByColor(oppositeColor);

        final Optional<Move> piecesToAttackKing = piecesFromOpponent.entrySet().stream()
                .flatMap(entry -> Moves.constructAttackMoves(entry.getValue(), entry.getKey(), kingSquare.get()).stream())
                .filter(board::isValidMove)
                .findFirst();

        return piecesToAttackKing.isPresent();
    }

}
