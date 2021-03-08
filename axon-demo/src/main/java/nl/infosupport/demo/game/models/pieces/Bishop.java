package nl.infosupport.demo.game.models.pieces;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.strategies.AcrossMovingStrategy;
import nl.infosupport.demo.game.models.strategies.MovingStrategy;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Bishop extends Piece {
    public Bishop(ChessColor color) {
        super(color);
    }

    public static Piece black() {
        return new Bishop(ChessColor.BLACK);
    }

    public static Piece white() {
        return new Bishop(ChessColor.WHITE);
    }

    @Override
    protected List<MovingStrategy> getMovingStrategies() {
        return List.of(new AcrossMovingStrategy());
    }
}
