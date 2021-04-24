package nl.infosupport.demo.game.command.models;

import nl.infosupport.demo.game.command.models.pieces.*;
import org.springframework.stereotype.Service;

@Service
public class PiecesFactory {

    private static final String BISHOP = "BISHOP";
    private static final String KING = "KING";
    private static final String KNIGHT = "KNIGHT";
    private static final String PAWN = "PAWN";
    private static final String QUEEN = "QUEEN";
    private static final String ROOK = "ROOK";

    public Piece getPiece(String pieceType, ChessColor chessColor) {
        return switch (pieceType.toUpperCase()) {
            case BISHOP -> new Bishop(chessColor);
            case KING -> new King(chessColor);
            case KNIGHT -> new Knight(chessColor);
            case PAWN -> new Pawn(chessColor);
            case QUEEN -> new Queen(chessColor);
            case ROOK -> new Rook(chessColor);
            default -> throw new RuntimeException("Unknown chess piece for type: " + pieceType);
        };
    }
}
