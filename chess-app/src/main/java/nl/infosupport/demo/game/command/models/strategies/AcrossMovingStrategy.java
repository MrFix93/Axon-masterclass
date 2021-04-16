package nl.infosupport.demo.game.command.models.strategies;

import nl.infosupport.demo.game.command.models.Navigator;
import nl.infosupport.demo.game.command.models.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcrossMovingStrategy implements MovingStrategy {

    private final RangeMode rangeMode;

    public AcrossMovingStrategy() {
        this.rangeMode = RangeMode.UNLIMITED;
    }

    public AcrossMovingStrategy(RangeMode rangeMode) {
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
        List<Square> squares = new ArrayList<>();
        getNavigator().acrossForwards(currentSquare, Navigator.Direction.LEFT).ifPresent(squares::add);
        getNavigator().acrossForwards(currentSquare, Navigator.Direction.RIGHT).ifPresent(squares::add);
        getNavigator().acrossBackwards(currentSquare, Navigator.Direction.LEFT).ifPresent(squares::add);
        getNavigator().acrossBackwards(currentSquare, Navigator.Direction.RIGHT).ifPresent(squares::add);
        return squares;
    }

    private List<Square> getUnlimitedRange(Square currentSquare) {
        final List<Square> acrossLForwardsSquares = getAcrossForwardsSquares(currentSquare, Navigator.Direction.LEFT, new ArrayList<>());
        final List<Square> acrossRForwardsSquares = getAcrossForwardsSquares(currentSquare, Navigator.Direction.RIGHT, new ArrayList<>());
        final List<Square> acrossLBackwardsSquares = getAcrossBackwardsSquares(currentSquare, Navigator.Direction.LEFT, new ArrayList<>());
        final List<Square> acrossRBackwardsSquares = getAcrossBackwardsSquares(currentSquare, Navigator.Direction.RIGHT, new ArrayList<>());

        List<Square> result = new ArrayList<>();
        result.addAll(acrossLForwardsSquares);
        result.addAll(acrossRForwardsSquares);
        result.addAll(acrossLBackwardsSquares);
        result.addAll(acrossRBackwardsSquares);

        return result;
    }

    private List<Square> getAcrossForwardsSquares(Square currentSquare, Navigator.Direction left, List<Square> squares) {
        final Optional<Square> square = getNavigator().acrossForwards(currentSquare, left);
        if (square.isEmpty()) {
            return squares;
        } else {
            square.ifPresent(squares::add);
            return getAcrossForwardsSquares(square.get(), left, squares);
        }
    }

    private List<Square> getAcrossBackwardsSquares(Square currentSquare, Navigator.Direction left, List<Square> squares) {
        final Optional<Square> square = getNavigator().acrossBackwards(currentSquare, left);
        if (square.isEmpty()) {
            return squares;
        } else {
            square.ifPresent(squares::add);
            return getAcrossBackwardsSquares(square.get(), left, squares);
        }
    }
}
