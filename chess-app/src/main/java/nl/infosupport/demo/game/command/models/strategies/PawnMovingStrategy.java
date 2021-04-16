package nl.infosupport.demo.game.command.models.strategies;

import nl.infosupport.demo.game.command.exceptions.UnexpectedChessException;
import nl.infosupport.demo.game.command.models.ChessColor;
import nl.infosupport.demo.game.command.models.Navigator;
import nl.infosupport.demo.game.command.models.Rank;
import nl.infosupport.demo.game.command.models.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PawnMovingStrategy implements MovingStrategy {

    private final Navigator navigator;

    public PawnMovingStrategy(ChessColor perspective) {
        this.navigator = new Navigator(perspective);
    }

    @Override
    public List<Square> getRange(Square currentSquare) {
        final Rank startRank = navigator.getPawnStartRank();

        final List<Square> range = new ArrayList<>();
        final Optional<Square> firstSquare = navigator.up(currentSquare);

        if (firstSquare.isPresent()) {
            range.add(firstSquare.get());

            if (currentSquare.getRank().equals(startRank)) {
                final Optional<Square> secondSquare = navigator.up(firstSquare.get());
                range.add(secondSquare.orElseThrow(UnexpectedChessException::new));
            }
        }

        return range;
    }

    @Override
    public List<Square> getAttackRange(Square currentSquare) {
        final Optional<Square> nextSquare = navigator.up(currentSquare);
        if (nextSquare.isEmpty()) {
            return Collections.emptyList();
        }

        final Optional<Square> leftAcross = navigator.acrossForwards(currentSquare, Navigator.Direction.LEFT);
        final Optional<Square> rightAcross = navigator.acrossForwards(currentSquare, Navigator.Direction.RIGHT);

        return List.of(leftAcross, rightAcross).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Navigator getNavigator() {
        return this.navigator;
    }

}
