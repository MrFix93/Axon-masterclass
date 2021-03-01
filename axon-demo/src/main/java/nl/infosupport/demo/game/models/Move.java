package nl.infosupport.demo.game.models;

import com.google.common.collect.Streams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode
public class Move {
    private final Piece piece;
    private final Square startSquare;
    private final Square endSquare;
    private final MoveType moveType;

    public void make(Board board) throws IllegalChessMoveException {
        if (!board.pieceIsAtSquare(startSquare, piece)) {
            throw new IllegalChessMoveException("Piece cannot make this move", this);
        }

        if (board.targetSquareIsOccupiedByTheSameColor(endSquare, getMoveColor())) {
            throw new IllegalChessMoveException("Target square is occupied by piece of the same color", this);
        }

        if (!piece.isAbleToMake(this)) {
            throw new IllegalChessMoveException("Piece cannot make move", this);
        }

        if (!piece.canJumpOverPiece() && isObstructedMove(board)) {
            throw new IllegalChessMoveException("Path is obstructed", this);
        }

        board.updateBoard(this);

        if (board.isCheck(piece.getColor())) {
            throw new IllegalChessMoveException("Unable to make move because you are check", this);
        }
    }

    public enum MoveType {
        CAPTURE,
        NORMAL
    }

    public Move(Piece piece, Square startSquare, Square endSquare) {
        this.piece = piece;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.moveType = MoveType.NORMAL;
    }

    public Move(Piece piece, Square startSquare, Square endSquare, MoveType moveType) {
        this.piece = piece;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.moveType = moveType;
    }

    public ChessColor getMoveColor() {
        return piece.getColor();
    }

    public boolean isObstructedMove(Board board) {
        Stream<Integer> ranks = IntStream.rangeClosed(startSquare.rank, endSquare.rank)
                .boxed();
        Stream<File> files = EnumSet.range(startSquare.file, endSquare.file)
                .stream().sorted();

        final List<Square> travelingPath = Streams.zip(files, ranks, Square::new)
                .collect(Collectors.toList());

        for (Square square : travelingPath) {
            final Piece pieceAtSquare = board.tilePieceMap.get(square);
            if (pieceAtSquare != null) {
                return false;
            }
        }

        return true;
    }
}
