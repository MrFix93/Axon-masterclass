package nl.infosupport.demo.game.utils;

import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.Square;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Moves {

    public static List<Move> fromPosition(Piece piece, Square startSquare) {
        final List<Square> normalRange = piece.getRange(startSquare);
        final List<Square> attackRange = piece.getAttackRange(startSquare);

        return Stream.of(normalRange, attackRange)
                .flatMap(Collection::stream)
                .map(endSquare -> new Move(piece, startSquare, endSquare))
                .collect(Collectors.toList());
    }
}
