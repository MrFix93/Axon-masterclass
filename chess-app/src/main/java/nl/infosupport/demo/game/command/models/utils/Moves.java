package nl.infosupport.demo.game.command.models.utils;

import nl.infosupport.demo.game.command.models.Move;
import nl.infosupport.demo.game.command.models.Piece;
import nl.infosupport.demo.game.command.models.Square;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Moves {

    private Moves() {
    }

    public static List<Move> constructAllMoves(Piece piece, Square startSquare) {
        final List<Square> normalRange = piece.getRange(startSquare);
        final List<Square> attackRange = piece.getAttackRange(startSquare);

        return Stream.of(normalRange, attackRange)
                .flatMap(Collection::stream)
                .map(endSquare -> new Move(piece, startSquare, endSquare))
                .collect(Collectors.toList());
    }

    public static List<Move> constructAttackMoves(Piece piece, Square startSquare, Square endSquare) {
        if (piece.getAttackRange(startSquare).contains(endSquare)) {
            return List.of(new Move(piece, startSquare, endSquare));
        }

        return Collections.emptyList();
    }

}
