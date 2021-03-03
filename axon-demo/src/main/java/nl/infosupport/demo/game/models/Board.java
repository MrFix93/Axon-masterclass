package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.infosupport.demo.game.models.pieces.King;
import nl.infosupport.demo.game.utils.Moves;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Board {
    @Getter
    private final Map<Square, Piece> tilePieceMap;

    public Board(Map<Square, Piece> tilePieceMap) {
        this.tilePieceMap = tilePieceMap;
    }

    public boolean isCheckMate(ChessColor color) {
        final Map<Square, Piece> pieces = getPieceByColor(color);

        final Optional<Move> eligibleMove = pieces.entrySet()
                .stream()
                .flatMap(position -> Moves.fromPosition(position.getValue(), position.getKey()).stream())
                .filter(move -> move.isValidMove(this))
                .findFirst();

        return eligibleMove.isEmpty();
    }

    public boolean isCheck(ChessColor color) {
        final Optional<Square> kingSquare = getKingOfColor(color);

        if (kingSquare.isEmpty()) {
            return false;
        }

        final ChessColor oppositeColor = color.invert();
        final Map<Square, Piece> piecesFromOpponent = getPieceByColor(oppositeColor);

        final Optional<Move> piecesToAttackKing = piecesFromOpponent.entrySet()
                .stream()
                .filter(position -> position.getValue().getAttackRange(position.getKey()).contains(kingSquare.get()))
                .map(position -> new Move(position.getValue(), position.getKey(), kingSquare.get()))
                .filter(move -> move.isValidMove(this))
                .findFirst();

        return piecesToAttackKing.isPresent();
    }

    public Optional<Square> getKingOfColor(ChessColor color) {
        return tilePieceMap.entrySet()
                .stream()
                .filter(position -> position.getValue().getColor().equals(color))
                .filter(entry -> entry.getValue().getClass() == King.class)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public void updateBoard(Move move) {
        tilePieceMap.remove(move.getStartSquare());
        tilePieceMap.put(move.getEndSquare(), move.getPiece());
    }

    public Optional<Piece> getPieceBySquare(Square square) {
        return tilePieceMap.entrySet()
                .stream()
                .filter(position -> position.getKey().equals(square))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public Map<Square, Piece> getPieceByColor(ChessColor color) {
        return tilePieceMap.entrySet()
                .stream()
                .filter(position -> position.getValue().getColor().equals(color))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<Piece> getKingOfColor(Square square) {
        final boolean containsAPiece = tilePieceMap.containsKey(square);
        return containsAPiece ? Optional.of(tilePieceMap.get(square)) : Optional.empty();
    }

    boolean targetSquareIsOccupiedByTheSameColor(Square square, ChessColor color) {
        final Optional<Piece> pieceAtTargetSquare = this.getKingOfColor(square);
        return pieceAtTargetSquare.isPresent() && pieceAtTargetSquare.get().getColor() == color;
    }

    Square getSquare(File file, Rank rank) {
        final Optional<Square> existingSquare = tilePieceMap.keySet()
                .stream()
                .filter(square -> square.getRank().equals(rank) && square.getFile().equals(file)).findFirst();

        return existingSquare.orElseGet(() -> new Square(file, rank));

    }
}
