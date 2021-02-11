package nl.infosupport.demo.game.models;

public class Position {
    Square square;
    Piece piece;

    public Position(Square square, Piece piece) {
        this.square = square;
        this.piece = piece;
    }
}
