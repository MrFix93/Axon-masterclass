package nl.infosupport.demo.game.models;

import nl.infosupport.demo.game.models.pieces.Paw;

public class BoardCreator {

    public static Board singlePawBoard() {
        final Board board = new Board();
        board.tilePieceMap.put(new Square(File.a, 1), new Paw(ChessColor.WHITE));
        return board;
    }
}
