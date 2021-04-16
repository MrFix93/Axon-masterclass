package nl.infosupport.demo.game.models.pieces;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.strategies.HorizontalMovingStrategy;
import nl.infosupport.demo.game.models.strategies.MovingStrategy;
import nl.infosupport.demo.game.models.strategies.VerticalMovingStrategy;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Rook extends Piece {
    public Rook(ChessColor color) {
        super(color);
    }

    public static Piece black() {
        return new Rook(ChessColor.BLACK);
    }

    public static Piece white() {
        return new Rook(ChessColor.WHITE);
    }

    @Override
    protected List<MovingStrategy> getMovingStrategies() {
        return List.of(new HorizontalMovingStrategy(MovingStrategy.RangeMode.UNLIMITED),
                new VerticalMovingStrategy(MovingStrategy.RangeMode.UNLIMITED));
    }
}
