package nl.infosupport.demo.game.models.rules;

import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Piece;

public class MoveShouldNotResultInCheckStateRule implements ChessMoveRule {

    @Override
    public boolean isValid(Move move, Board board) {
        final Piece piece = move.getPiece();

        final Board newBoard = Board.copy(board);
        newBoard.commit(move);

        return !CheckMateRule.isCheck(newBoard, piece.getColor());
    }

    @Override
    public String getFailureReason() {
        return "After this move your King could be captured";
    }

}
