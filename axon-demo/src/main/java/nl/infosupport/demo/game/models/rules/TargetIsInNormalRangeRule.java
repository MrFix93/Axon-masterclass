package nl.infosupport.demo.game.models.rules;

import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Piece;

public class TargetIsInNormalRangeRule implements ChessMoveRule {
    @Override
    public boolean isValid(Move move, Board board) {
        if (board.getMoveType(move) == Move.MoveType.CAPTURE) {
            return true;
        }

        final Piece piece = move.getPiece();

        return piece.getRange(move.getStartSquare()).contains(move.getEndSquare());
    }

    @Override
    public String getFailureReason() {
        return "Piece cannot make move";
    }

}
