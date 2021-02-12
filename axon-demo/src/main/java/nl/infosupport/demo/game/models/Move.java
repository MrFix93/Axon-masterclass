package nl.infosupport.demo.game.models;

import com.google.common.collect.Streams;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode
public class Move {
    Piece piece;
    Square startSquare;
    Square endSquare;
    MoveType moveType;

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

    public TravelPath getTravelingPaths(Board board) {
        Stream<Integer> ranks = IntStream.rangeClosed(startSquare.rank, endSquare.rank)
                .boxed();
        Stream<File> files = EnumSet.range(startSquare.file, endSquare.file)
                .stream().sorted();

        final List<Square> travelingPath = Streams.zip(files, ranks, Square::new)
                .collect(Collectors.toList());

        return new TravelPath(travelingPath, board);
    }
}
