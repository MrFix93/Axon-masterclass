package nl.infosupport.demo.game.models.pieces;

import lombok.EqualsAndHashCode;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.strategies.KnightJumpMovingStrategy;
import nl.infosupport.demo.game.models.strategies.MovingStrategy;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Knight extends Piece {
    public Knight(ChessColor color) {
        super(color);
    }

    public static Piece black() {
        return new Knight(ChessColor.BLACK);
    }

    @Override
    public boolean canJumpOverPiece() {
        return true;
    }

    public static Piece white() {
        return new Knight(ChessColor.WHITE);
    }

    @Override
    protected List<MovingStrategy> getMovingStrategies() {
        return List.of(new KnightJumpMovingStrategy());
    }
}
