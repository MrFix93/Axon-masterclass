package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.models.pieces.King;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Board {
    Map<Square, Piece> tilePieceMap = new HashMap<>();

    boolean pieceIsAtSquare(Square square, Piece piece) {
        return tilePieceMap.containsKey(square) && tilePieceMap.get(square).equals(piece);
    }

    public boolean isCheck(ChessColor color) {
        final ChessColor oppositeColor = color.invert();
        final King king = new King(oppositeColor);

        final Map<Piece, Square> piecesFromOpponent = getPieceByColor(oppositeColor);
        final Square kingSquare = piecesFromOpponent.get(king);

        if(kingSquare == null) {
            return false;
        }

        final List<Move> availableMoves = piecesFromOpponent.entrySet()
                .stream()
                .map(position -> new Move(position.getKey(), position.getValue(), kingSquare))
                .filter(move -> {
                    try {
                        move.make(this);
                    } catch (IllegalChessMoveException e) {
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList());

        return !availableMoves.isEmpty();
    }

    public void updateBoard(Move move) {
        tilePieceMap.remove(move.getStartSquare());
        tilePieceMap.put(move.getEndSquare(), move.getPiece());
    }

    public Map<Piece, Square> getPieceByColor(ChessColor color) {
        return tilePieceMap.entrySet()
                .stream()
                .filter(position -> position.getValue().getColor().equals(color))
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public Optional<Piece> getPiece(Square square) {
        final boolean containsAPiece = tilePieceMap.containsKey(square);
        return containsAPiece ? Optional.of(tilePieceMap.get(square)) : Optional.empty();
    }

    boolean targetSquareIsOccupiedByTheSameColor(Square square, ChessColor color) {
        final Optional<Piece> pieceAtTargetSquare = this.getPiece(square);
        return pieceAtTargetSquare.isPresent() && pieceAtTargetSquare.get().getColor() == color;
    }
}
