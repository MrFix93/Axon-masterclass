package nl.infosupport.demo.game.command.utils;

import com.google.common.collect.Streams;
import nl.infosupport.demo.game.command.exceptions.IllegalBoardSquareException;
import nl.infosupport.demo.game.command.exceptions.UnexpectedChessException;
import nl.infosupport.demo.game.command.models.File;
import nl.infosupport.demo.game.command.models.Move;
import nl.infosupport.demo.game.command.models.Rank;
import nl.infosupport.demo.game.command.models.Square;
import nl.infosupport.demo.game.command.models.strategies.KnightJumpMovingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Path {

    private Path() {
    }

    public static List<Square> constructFrom(Move move) {
        final List<Rank> ranks;
        final Square startSquare = move.getStartSquare();
        final Square endSquare = move.getEndSquare();
        if (startSquare.getRank().getOrdinal() > endSquare.getRank().getOrdinal()) {
            ranks = IntStream.rangeClosed(endSquare.getRank().getOrdinal(), startSquare.getRank().getOrdinal())
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .map(Path::mapToRank)
                    .collect(Collectors.toList());
        } else {
            ranks = IntStream.rangeClosed(startSquare.getRank().getOrdinal(), endSquare.getRank().getOrdinal())
                    .boxed()
                    .map(Path::mapToRank)
                    .collect(Collectors.toList());
        }

        final List<File> files;
        if (startSquare.getFile().compareTo(endSquare.getFile()) > 0) {
            files = EnumSet.range(endSquare.getFile(), startSquare.getFile()).stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        } else {
            files = new ArrayList<>(EnumSet.range(startSquare.getFile(), endSquare.getFile()));
        }

        final boolean knightJump = KnightJumpMovingStrategy.isKnightJump(ranks, files);
        if (knightJump) {
            return List.of(startSquare, endSquare);
        }

        return getPath(ranks, files);
    }

    private static List<Square> getPath(List<Rank> ranks, List<File> files) {
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

        return Streams.zip(workFiles.stream(), workRanks.stream(), Square::new).collect(Collectors.toList());
    }

    private static Rank mapToRank(int intRank) {
        try {
            return new Rank(intRank);
        } catch (IllegalBoardSquareException e) {
            throw new UnexpectedChessException("Could not define range", e);
        }
    }
}
