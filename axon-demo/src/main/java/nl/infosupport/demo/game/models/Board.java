package nl.infosupport.demo.game.models;

import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;

import java.util.HashMap;
import java.util.Map;

public class Board {
    Map<Tile, Piece> tilePieceMap = new HashMap<>();

    public static Board singlePawBoard() {
        final Board board = new Board();
        board.tilePieceMap.put(new Tile(File.a, 1), new Paw(ChessColor.WHITE));
        return board;
    }

    public void move(Move move) throws IllegalChessMoveException {
        if(!tilePieceMap.get(move.startTile).equals(move.piece)) {
            throw new IllegalChessMoveException("Piece is not at the start tile", move);
        }

        if(!move.isValidMove()) {
            throw new IllegalChessMoveException("Piece cannot make this move", move);
        }

        updateBoard(move);
    }

    private void updateBoard(Move move) {
        tilePieceMap.remove(move.startTile);
        tilePieceMap.put(move.endTile, move.piece);
    }
}
