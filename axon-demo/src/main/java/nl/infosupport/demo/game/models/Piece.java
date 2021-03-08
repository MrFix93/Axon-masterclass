package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.models.strategies.MovingStrategy;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode
public abstract class Piece {
    ChessColor color;

    protected Piece(ChessColor color) {
        this.color = color;
    }

    public ChessColor getColor() {
        return color;
    }

    protected abstract List<MovingStrategy> getMovingStrategies();

    public List<Square> getRange(Square currentSquare) {
        return collectSquaresFromStrategies(strategy -> strategy.getRange(currentSquare));
    }

    public List<Square> getAttackRange(Square currentSquare) {
        return collectSquaresFromStrategies(strategy -> strategy.getAttackRange(currentSquare));
    }

    private List<Square> collectSquaresFromStrategies(Function<MovingStrategy, List<Square>> movingStrategyListFunction) {
        return getMovingStrategies().stream().map(movingStrategyListFunction)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public boolean canJumpOverPiece() {
        return false;
    }
}
