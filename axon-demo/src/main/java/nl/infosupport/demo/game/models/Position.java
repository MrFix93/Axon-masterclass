package nl.infosupport.demo.game.models;

public class Position {
    Tile tile;
    Piece piece;

    public Position(Tile tile, Piece piece) {
        this.tile = tile;
        this.piece = piece;
    }
}
