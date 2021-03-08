package nl.infosupport.demo.game.models.rules;

import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.Move;

public class PieceShouldBeOnStartSquareRule implements ChessMoveRule {
    @Override
    public boolean isValid(Move move, Board board) {
        return board.getPieceBySquare(move.getStartSquare()).isPresent() && board.getPieceBySquare(move.getStartSquare()).get().equals(move.getPiece());
    }

    @Override
    public String getFailureReason() {
        return "Piece cannot make this move because this piece is not at this square";
    }

}
