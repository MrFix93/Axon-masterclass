package nl.infosupport.demo.game.command.models.pieces;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.command.models.ChessColor;
import nl.infosupport.demo.game.command.models.Piece;
import nl.infosupport.demo.game.command.models.strategies.AcrossMovingStrategy;
import nl.infosupport.demo.game.command.models.strategies.MovingStrategy;

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
