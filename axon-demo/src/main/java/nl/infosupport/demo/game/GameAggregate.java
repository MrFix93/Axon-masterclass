package nl.infosupport.demo.game;

import nl.infosupport.demo.game.commands.MakeMoveCommand;
import nl.infosupport.demo.game.commands.StartGameCommand;
import nl.infosupport.demo.game.events.GameStartedEvent;
import nl.infosupport.demo.game.models.*;
import nl.infosupport.demo.game.models.rules.*;
import nl.infosupport.demo.game.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.printer.FancyBoardPrinter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class GameAggregate {

    @AggregateIdentifier
    private String id;
    private Board board;
    private GameState gameState;

    public static final List<ChessMoveRule> rules = List.of(
            new TargetIsInAttackRangeRule(),
            new TargetIsInNormalRangeRule(),
            new PieceShouldBeOnStartSquareRule(),
            new PieceCanNotAttackOwnColorRule(),
            new PathIsObstructedRule(),
            new MoveShouldNotResultInCheckStateRule()
    );

    private final ConsoleBoardPrinter printer = new FancyBoardPrinter(System.out);

    private Player whitePlayer;
    private Player blackPlayer;
    private List<Move> movesMade = new ArrayList<>();

    private ChessColor firstMove;

    public GameAggregate() {
        // Axon requires no args constructor
    }

    @CommandHandler
    @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(StartGameCommand command) {
        final Player player1 = new Player(command.getWhitePlayer(), ChessColor.WHITE);
        final Player player2 = new Player(command.getBlackPlayer(), ChessColor.BLACK);

        apply(new GameStartedEvent(command.getId(), BoardCreator.fullBoard(), GameState.STARTED, player1, player2, ChessColor.WHITE));
    }

    @CommandHandler
    public void handle(MakeMoveCommand command) {
        /**
         * 1. Game moet in juiste staat zijn
         * 2. Speler die de move maakt moet aan de beurt zijn
         * 3. Spelers kunnen alleen stukken gebruiken van hun eigen kleur
         * 4. De move moet valide zijn
         * 5. De move moet gemaakt worden
         * 6. Check  of het schaakmat is, zo ja, beëindig de game
         */
    }

    private boolean inTurn(ChessColor color) {
        return !((movesMade.size() + 1) % 2 == 1 && color == firstMove.invert());
    }

    @EventSourcingHandler
    public void handle(GameStartedEvent event) {
        /**
         * Vul de instance variabelen met data uit het event
         */
        printer.print(board);
    }
}
