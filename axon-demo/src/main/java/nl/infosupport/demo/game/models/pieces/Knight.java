package nl.infosupport.demo.game.models.pieces;

import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Piece;

public class Knight extends Piece {
    public Knight(ChessColor color) {
        super(color);
    }

    @Override
    public boolean isAbleToMake(Move move) {
        return false;
    }

    @Override
    public boolean canJumpOverPiece() {
        return true;
    }
}
