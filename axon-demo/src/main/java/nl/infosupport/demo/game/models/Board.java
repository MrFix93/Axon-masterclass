package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.GameAggregate;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.models.rules.ChessMoveRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Board {
    private final Map<Square, Piece> piecesOnSquare;

    public Board(Map<Square, Piece> piecesOnSquare) {
        this.piecesOnSquare = piecesOnSquare;
    }

    public static Board copy(Board board) {
        return new Board(new HashMap<>(board.piecesOnSquare));
    }

    public void commit(Move move) {
        piecesOnSquare.remove(move.getStartSquare());
        piecesOnSquare.put(move.getEndSquare(), move.getPiece());
    }

    public void make(Move move) throws IllegalChessMoveException {
        for (ChessMoveRule rule : GameAggregate.rules) {
            final boolean moveAdheresRule = rule.isValid(move, this);
            if (!moveAdheresRule) {
                throw new IllegalChessMoveException(rule.getFailureReason(), move, this);
            }
        }
    }

    public boolean isValidMove(Move move) {
        return GameAggregate.rules.stream().allMatch(rule -> rule.isValid(move, this));
    }

    public Move.MoveType getMoveType(Move move) {
        final Optional<Piece> pieceBySquare = getPieceBySquare(move.getEndSquare());
        if (pieceBySquare.isPresent() && pieceBySquare.get().getColor() == move.getPiece().getColor().invert()) {
            return Move.MoveType.CAPTURE;
        }
        return Move.MoveType.NORMAL;
    }

    public Optional<Square> getPieceByTypeAndColor(ChessColor color, Class<? extends Piece> pieceType) {
        return getPieceByColor(color).entrySet().stream()
                .filter(entry -> entry.getValue().getClass() == pieceType)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Optional<Piece> getPieceBySquare(Square square) {
        return piecesOnSquare.entrySet().stream()
                .filter(position -> position.getKey().equals(square))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public Map<Square, Piece> getPieceByColor(ChessColor color) {
        return piecesOnSquare.entrySet().stream()
                .filter(position -> position.getValue().getColor().equals(color))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
