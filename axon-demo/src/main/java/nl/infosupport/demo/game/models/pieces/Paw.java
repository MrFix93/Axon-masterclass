package nl.infosupport.demo.game.models.pieces;

import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.Square;

import java.util.List;

public class Paw extends Piece {
    public Paw(ChessColor color) {
        super(color);
    }

    @Override
    public boolean isAbleToMake(Move move) {
        return endSquareIsInRange(move);
    }

    @Override
    public boolean canJumpOverPiece() {
        return false;
    }

    private boolean endSquareIsInRange(Move move) {
        return getRange(move.getStartSquare(), move.getMoveType()).contains(move.getEndSquare());
    }

    private List<Square> getRange(Square currentSquare, Move.MoveType moveType) {
        if (moveType.equals(Move.MoveType.CAPTURE)) {
            return List.of(
                    new Square(currentSquare.getFile().left(), currentSquare.getRank() + 1),
                    new Square(currentSquare.getFile().right(), currentSquare.getRank() + 1)
            );
        } else {
            return List.of(new Square(currentSquare.getFile(), currentSquare.getRank() + 1));
        }
    }
}
