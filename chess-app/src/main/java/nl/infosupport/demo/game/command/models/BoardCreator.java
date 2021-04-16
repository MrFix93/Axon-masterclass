package nl.infosupport.demo.game.command.models;

import nl.infosupport.demo.game.command.models.pieces.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BoardCreator {

    public static Board empty() {
        return new Board(Collections.emptyMap());
    }

    public static Board singlePawBoard() {
        final HashMap<Square, Piece> boardMap = new HashMap<>();
        boardMap.put(new Square(File.a, Rank.one), new Pawn(ChessColor.WHITE));

        return new Board(boardMap);
    }

    public static Board fullBoard() {
        final HashMap<Square, Piece> boardMap = new HashMap<>();
        boardMap.putAll(placePawns(Rank.two, ChessColor.WHITE));
        boardMap.putAll(placePawns(Rank.seven, ChessColor.BLACK));
        boardMap.putAll(placePieces(Rank.one, ChessColor.WHITE));
        boardMap.putAll(placePieces(Rank.eight, ChessColor.BLACK));
        return new Board(boardMap);
    }

    private static Map<Square, Piece> placePieces(Rank rank, ChessColor white) {
        final HashMap<Square, Piece> boardMap = new HashMap<>();
        boardMap.put(new Square(File.a, rank), new Rook(white));
        boardMap.put(new Square(File.b, rank), new Knight(white));
        boardMap.put(new Square(File.c, rank), new Bishop(white));
        boardMap.put(new Square(File.d, rank), new Queen(white));
        boardMap.put(new Square(File.e, rank), new King(white));
        boardMap.put(new Square(File.f, rank), new Bishop(white));
        boardMap.put(new Square(File.g, rank), new Knight(white));
        boardMap.put(new Square(File.h, rank), new Rook(white));
        return boardMap;
    }

    private static Map<Square, Piece> placePawns(Rank rank, ChessColor chessColor) {
        final HashMap<Square, Piece> boardMap = new HashMap<>();
        boardMap.put(new Square(File.a, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.b, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.c, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.d, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.e, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.f, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.g, rank), new Pawn(chessColor));
        boardMap.put(new Square(File.h, rank), new Pawn(chessColor));
        return boardMap;
    }

    public static class Builder {
        Map<Square, Piece> tilePieceMap = new HashMap<>();

        public Builder with(Square square, Piece piece) {
            this.tilePieceMap.put(square, piece);

            return this;
        }

        public Board build() {
            return new Board(tilePieceMap);
        }
    }
}
