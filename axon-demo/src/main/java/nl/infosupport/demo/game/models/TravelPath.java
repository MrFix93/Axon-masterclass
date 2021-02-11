package nl.infosupport.demo.game.models;

import com.google.common.collect.Streams;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TravelPath {
    Map<Square, Piece> travelingPath = new HashMap<>();

    public TravelPath determineFromMove(Move move, Board board) {
        Stream<Integer> ranks = IntStream.rangeClosed(move.getStartSquare().rank, move.getEndSquare().rank)
                .boxed();
        Stream<File> files = EnumSet.range(move.getStartSquare().file, move.getEndSquare().file)
                .stream().sorted();

        final List<Square> travelingPath = Streams.zip(files, ranks, Square::new)
                .collect(Collectors.toList());

        return new TravelPath(travelingPath, board);
    }

    public TravelPath(List<Square> path, Board board) {
        final Map<Square, Piece> tilePieceMap = board.tilePieceMap;

        path.forEach(square -> {
            final Piece piece = tilePieceMap.get(square);
            if (piece != null) {
                travelingPath.put(square, piece);
            }
        });
    }

    public boolean pathIsObstructed() {
        return travelingPath.values().stream().anyMatch(Objects::nonNull);
    }
}
