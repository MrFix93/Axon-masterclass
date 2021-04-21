package nl.infosupport.demo.game.printer;

import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.pieces.*;

public class UnicodePiecePrinter implements PiecePrinter<String> {

    private static final String WHITE_PAWN = "\u2659";
    private static final String BLACK_PAWN = "\u265F";
    private static final String WHITE_KING = "\u2654";
    private static final String BLACK_KING = "\u265A";
    private static final String WHITE_KNIGHT = "\u2658";
    private static final String BLACK_KNIGHT = "\u265E";
    private static final String BLACK_ROOK = "\u265C";
    private static final String WHITE_ROOK = "\u2656";
    private static final String WHITE_BISHOP = "\u2657";
    private static final String BLACK_BISHOP = "\u265D";
    private static final String WHITE_QUEEN = "\u2655";
    private static final String BLACK_QUEEN = "\u265B";

    @Override
    public String print(Piece piece) {
        if (piece instanceof Pawn) {
            return this.printPiece((Pawn) piece);
        }
        if (piece instanceof Rook) {
            return this.printPiece((Rook) piece);
        }
        if (piece instanceof Bishop) {
            return this.printPiece((Bishop) piece);
        }
        if (piece instanceof King) {
            return this.printPiece((King) piece);
        }
        if (piece instanceof Knight) {
            return this.printPiece((Knight) piece);
        }
        if (piece instanceof Queen) {
            return this.printPiece((Queen) piece);
        }

        return "?";
    }

    public String printPiece(Pawn piece) {
        return piece.getColor() == ChessColor.WHITE ? WHITE_PAWN : BLACK_PAWN;
    }

    public String printPiece(King piece) {
        return piece.getColor() == ChessColor.WHITE ? WHITE_KING : BLACK_KING;
    }

    public String printPiece(Knight piece) {
        return piece.getColor() == ChessColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT;
    }

    public String printPiece(Rook piece) {
        return piece.getColor() == ChessColor.WHITE ? WHITE_ROOK : BLACK_ROOK;
    }

    public String printPiece(Bishop piece) {
        return piece.getColor() == ChessColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP;
    }

    public String printPiece(Queen piece) {
        return piece.getColor() == ChessColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN;
    }
}
