package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode
public class Board {
    Map<Square, Piece> tilePieceMap = new HashMap<>();

    public void move(Move move) throws IllegalChessMoveException {
        final Piece piece = move.piece;
        final Square startSquare = move.startSquare;
        if (!pieceIsAtSquare(startSquare, piece)) {
            throw new IllegalChessMoveException("Piece cannot make this move", move);
        }

        final Square endSquare = move.endSquare;
        if (targetSquareIsOccupiedByTheSameColor(endSquare, move.getMoveColor())) {
            throw new IllegalChessMoveException("Target square is occupied by piece of the same color", move);
        }

        if (!piece.isAbleToMake(move)) {
            throw new IllegalChessMoveException("Piece cannot make move", move);
        }

        final TravelPath travelingPaths = move.getTravelingPaths(this);
        if (!piece.canJumpOverPiece() && travelingPaths.pathIsObstructed()) {
            throw new IllegalChessMoveException("Path is obstructed", move);
        }

        updateBoard(move);

        if (isCheck(piece.getColor())) {
            throw new IllegalChessMoveException("Unable to make move because you are check", move);
        }
    }

    private boolean pieceIsAtSquare(Square square, Piece piece) {
        return tilePieceMap.containsKey(square) && tilePieceMap.get(square).equals(piece);
    }

    public boolean isCheck(ChessColor color) {
        return false;
    }

    public void updateBoard(Move move) {
        tilePieceMap.remove(move.startSquare);
        tilePieceMap.put(move.endSquare, move.piece);
    }

    public Optional<Piece> getPiece(Square square) {
        final boolean containsAPiece = tilePieceMap.containsKey(square);
        return containsAPiece ? Optional.of(tilePieceMap.get(square)) : Optional.empty();
    }

    private boolean targetSquareIsOccupiedByTheSameColor(Square square, ChessColor color) {
        final Optional<Piece> pieceAtTargetSquare = this.getPiece(square);
        return pieceAtTargetSquare.isPresent() && pieceAtTargetSquare.get().getColor() == color;
    }
}
