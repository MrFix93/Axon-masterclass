package nl.infosupport.demo.game.models.pieces;

import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Piece;

public class King extends Piece {
    public King(ChessColor color) {
        super(color);
    }

    @Override
    public boolean isAbleToMake(Move move) throws IllegalChessMoveException {
        return false;
    }
}
