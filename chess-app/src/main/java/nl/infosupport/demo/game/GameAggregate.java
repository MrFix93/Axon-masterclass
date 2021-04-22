package nl.infosupport.demo.game;

import nl.infosupport.demo.game.command.models.Board;
import nl.infosupport.demo.game.command.models.GameState;
import nl.infosupport.demo.game.command.models.rules.*;
import nl.infosupport.demo.game.command.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.command.printer.FancyBoardPrinter;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
public class GameAggregate {

    @AggregateIdentifier
    private String id;
    private Board board;
    private GameState gameState;

    private final ConsoleBoardPrinter printer = new FancyBoardPrinter(System.out);

    public static List<ChessMoveRule> getGameRules() {
        return List.of(
                new TargetIsInAttackRangeRule(),
                new TargetIsInNormalRangeRule(),
                new PieceShouldBeOnStartSquareRule(),
                new PieceCanNotAttackOwnColorRule(),
                new PathIsObstructedRule(),
                new MoveShouldNotResultInCheckStateRule()
        );
    }
}
