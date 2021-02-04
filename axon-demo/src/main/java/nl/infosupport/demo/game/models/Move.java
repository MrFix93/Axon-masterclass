package nl.infosupport.demo.game.models;

public class Move {
    Piece piece;
    Tile startTile;
    Tile endTile;

    public Move(Piece piece, Tile startTile, Tile endTile) {
        this.piece = piece;
        this.startTile = startTile;
        this.endTile = endTile;
    }

    public boolean isValidMove() {
        return piece.ableToMakeMove(this);
    }
}
