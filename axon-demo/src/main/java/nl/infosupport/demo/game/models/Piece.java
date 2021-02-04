package nl.infosupport.demo.game.models;

public abstract class Piece {
    ChessColor color;

    public Piece(ChessColor color) {
        this.color = color;
    }

    public abstract boolean ableToMakeMove(Move move);
}
