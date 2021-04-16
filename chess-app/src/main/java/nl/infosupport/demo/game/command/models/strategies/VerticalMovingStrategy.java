package nl.infosupport.demo.game.command.models.strategies;

import nl.infosupport.demo.game.command.models.Rank;
import nl.infosupport.demo.game.command.models.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VerticalMovingStrategy implements MovingStrategy {

    private final RangeMode rangeMode;

    public VerticalMovingStrategy() {
        this.rangeMode = RangeMode.UNLIMITED;
    }

    public VerticalMovingStrategy(RangeMode rangeMode) {
        this.rangeMode = rangeMode;
    }

    @Override
    public List<Square> getRange(Square currentSquare) {
        if (rangeMode == RangeMode.UNLIMITED) {
            return getUnlimitedRange(currentSquare);
        } else {
            return getSingleStepRange(currentSquare);
        }
    }

    private List<Square> getSingleStepRange(Square currentSquare) {
        final List<Square> squares = new ArrayList<>();
        getNavigator().up(currentSquare).ifPresent(squares::add);
        getNavigator().down(currentSquare).ifPresent(squares::add);
        return squares;
    }

    private List<Square> getUnlimitedRange(Square currentSquare) {
        final List<Rank> ranks = List.of(Rank.one, Rank.two, Rank.three, Rank.four, Rank.five, Rank.six, Rank.seven, Rank.eight);

        return ranks.stream()
                .map(rank -> new Square(currentSquare.getFile(), rank))
                .filter(square -> !square.equals(currentSquare))
                .collect(Collectors.toList());
    }
}
