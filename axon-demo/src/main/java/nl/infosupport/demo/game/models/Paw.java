package nl.infosupport.demo.game.models;

public class Paw extends Piece {
    public Paw(ChessColor color) {
        super(color);
    }

    @Override
    public boolean ableToMakeMove(Move move) {
        return false;
    }
}
