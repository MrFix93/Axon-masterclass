package nl.infosupport.demo.game.command.models.pieces;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.infosupport.demo.game.command.models.ChessColor;
import nl.infosupport.demo.game.command.models.Piece;
import nl.infosupport.demo.game.command.models.strategies.MovingStrategy;
import nl.infosupport.demo.game.command.models.strategies.PawnMovingStrategy;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = true)
public class Pawn extends Piece {

    public Pawn(ChessColor color) {
        super(color);
    }

    public static Piece black() {
        return new Pawn(ChessColor.BLACK);
    }

    public static Piece white() {
        return new Pawn(ChessColor.WHITE);
    }

    @Override
    protected List<MovingStrategy> getMovingStrategies() {
        return List.of(new PawnMovingStrategy(super.getColor()));
    }
}
