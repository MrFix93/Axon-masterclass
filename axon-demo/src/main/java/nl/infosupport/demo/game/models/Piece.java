package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;

@EqualsAndHashCode
public abstract class Piece {
    ChessColor color;

    protected Piece(ChessColor color) {
        this.color = color;
    }

    public ChessColor getColor() {
        return color;
    }

    public abstract boolean isAbleToMake(Move move) throws IllegalChessMoveException;

    public boolean canJumpOverPiece() {
        return false;
    }
}
