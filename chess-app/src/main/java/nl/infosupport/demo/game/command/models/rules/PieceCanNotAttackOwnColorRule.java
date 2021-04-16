package nl.infosupport.demo.game.command.models.rules;

import nl.infosupport.demo.game.command.models.Board;
import nl.infosupport.demo.game.command.models.Move;
import nl.infosupport.demo.game.command.models.Piece;
import nl.infosupport.demo.game.command.models.Square;

import java.util.Optional;

public class PieceCanNotAttackOwnColorRule implements ChessMoveRule {

    @Override
    public boolean isValid(Move move, Board board) {
        final Square endSquare = move.getEndSquare();
        final Piece piece = move.getPiece();
        final Optional<Piece> pieceAtTargetSquare = board.getPieceBySquare(endSquare);

        return pieceAtTargetSquare.isEmpty() || pieceAtTargetSquare.get().getColor() != piece.getColor();
    }

    @Override
    public String getFailureReason() {
        return "Target square is occupied by piece of the same color";
    }
}