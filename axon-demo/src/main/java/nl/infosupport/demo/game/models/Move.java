package nl.infosupport.demo.game.models;

import com.google.common.collect.Streams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.infosupport.demo.game.exceptions.IllegalBoardSquareException;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.exceptions.UnexpectedChessException;
import nl.infosupport.demo.game.models.strategies.KnightJumpMovingStrategy;
import nl.infosupport.demo.game.utils.Squares;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@EqualsAndHashCode
@ToString
public class Move {
    private final Piece piece;
    private final Square startSquare;
    private final Square endSquare;

    public enum MoveType {
        CAPTURE,
        NORMAL
    }

    public Move(Piece piece, Square startSquare, Square endSquare) {
        this.piece = piece;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    public Move(Piece piece, String... patterns) {
        final List<Square> squares = Squares.asList(patterns);
        if (squares.size() != 2) {
            throw new IllegalArgumentException("Unable to instantiate move from patterns " + patterns);
        }

        this.piece = piece;
        this.startSquare = squares.get(0);
        this.endSquare = squares.get(1);
    }

    public void makeAndCommit(Board board) throws IllegalChessMoveException {
        make(board);
        commitMove(board);
    }

    public void make(Board board) throws IllegalChessMoveException {
        moveOrAttackPieceOn(board);

        Board newBoard = new Board(new HashMap<>(board.getTilePieceMap()));
        commitMove(newBoard);
        if (newBoard.isCheck(piece.getColor())) {
            throw new IllegalChessMoveException("Unable to make move because you are check", this);
        }
    }

    public boolean isValidMove(Board board) {
        try {
            make(board);
        } catch (IllegalChessMoveException e) {
            return false;
        }

        return true;
    }

    public MoveType getMoveType(Board board) {
        final Optional<Piece> pieceBySquare = board.getPieceBySquare(endSquare);
        if (pieceBySquare.isPresent() && pieceBySquare.get().getColor() == piece.getColor().invert()) {
            return MoveType.CAPTURE;
        }
        return MoveType.NORMAL;
    }

    public void moveOrAttackPieceOn(Board board) throws IllegalChessMoveException {
        if (getMoveType(board) == MoveType.CAPTURE) {
            attackPiece(board);
        } else {
            movePiece(board);
        }
    }

    public void commitMove(Board board) {
        board.updateBoard(this);
    }

    public void attackPiece(Board board) throws IllegalChessMoveException {
        if (!piece.getAttackRange(startSquare).contains(endSquare)) {
            throw new IllegalChessMoveException("Piece " + piece + " cannot make move " + this, this);
        }

        makeMove(board);
    }

    public void movePiece(Board board) throws IllegalChessMoveException {
        if (!piece.getRange(startSquare).contains(endSquare)) {
            throw new IllegalChessMoveException("Piece " + piece + " cannot make move " + this, this);
        }

        makeMove(board);
    }

    private void makeMove(Board board) throws IllegalChessMoveException {
        if (board.getPieceBySquare(endSquare).isPresent() && !piece.getAttackRange(startSquare).contains(endSquare)) {
            throw new IllegalChessMoveException("Piece " + piece + " cannot make move " + this, this);
        }
        if (board.getPieceBySquare(startSquare).isEmpty() || !board.getPieceBySquare(startSquare).get().equals(piece)) {
            throw new IllegalChessMoveException("Piece cannot make this move because this piece is not at this square", this);
        }

        if (board.targetSquareIsOccupiedByTheSameColor(endSquare, piece.getColor())) {
            throw new IllegalChessMoveException("Target square is occupied by piece of the same color", this);
        }

        if (!piece.canJumpOverPiece() && isObstructedMove(board)) {
            throw new IllegalChessMoveException("Path is obstructed", this);
        }
    }

    public boolean isObstructedMove(Board board) {
        final List<Square> travelingPath = getFullPath();
        travelingPath.remove(startSquare);
        travelingPath.remove(endSquare);

        for (Square square : travelingPath) {
            final Optional<Piece> pieceAtSquare = board.getPieceBySquare(square);
            if (pieceAtSquare.isPresent()) {
                return true;
            }
        }
        return false;
    }

    public List<Square> getFullPath() {
        final List<Rank> ranks;
        if (getStartSquare().getRank().getOrdinal() > getEndSquare().getRank().getOrdinal()) {
            ranks = IntStream.rangeClosed(getEndSquare().getRank().getOrdinal(), getStartSquare().getRank().getOrdinal())
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .map(this::mapToRank)
                    .collect(Collectors.toList());
        } else {
            ranks = IntStream.rangeClosed(getStartSquare().getRank().getOrdinal(), getEndSquare().getRank().getOrdinal())
                    .boxed()
                    .map(this::mapToRank)
                    .collect(Collectors.toList());
        }

        final List<File> files;
        if (getStartSquare().getFile().compareTo(getEndSquare().getFile()) > 0) {
            files = EnumSet.range(getEndSquare().getFile(), getStartSquare().getFile()).stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        } else {
            files = new ArrayList<>(EnumSet.range(getStartSquare().getFile(), getEndSquare().getFile()));
        }

        final boolean knightJump = KnightJumpMovingStrategy.isKnightJump(ranks, files);
        if (knightJump) {
            return List.of(getStartSquare(), getEndSquare());
        }

        return getPath(ranks, files);
    }

    public List<Square> getPath(List<Rank> ranks, List<File> files) {
        final List<Rank> workRanks = new ArrayList<>(ranks);
        final List<File> workFiles = new ArrayList<>(files);

        if (workRanks.size() != workFiles.size()) {
            if (ranks.size() == 1) {
                for (int i = 0; i < (workFiles.size() - 1); i++) {
                    workRanks.add(workRanks.get(0));
                }
            }

            if (workFiles.size() == 1) {
                for (int i = 0; i < (workRanks.size() - 1); i++) {
                    workFiles.add(workFiles.get(0));
                }
            }
        }

        if (workRanks.size() != workFiles.size()) {
            throw new RuntimeException(String.format("Merging failed, files and ranks not same size files= %s, ranks= %s", files, ranks));
        }

        return Streams.zip(workRanks.stream(), workFiles.stream(), Square::new).collect(Collectors.toList());
    }

    Rank mapToRank(int intRank) {
        try {
            return new Rank(intRank);
        } catch (IllegalBoardSquareException e) {
            throw new UnexpectedChessException("Could not define range", e);
        }
    }
}
