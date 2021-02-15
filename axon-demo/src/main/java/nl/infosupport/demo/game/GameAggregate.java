package nl.infosupport.demo.game;

import nl.infosupport.demo.game.commands.MakeMoveCommand;
import nl.infosupport.demo.game.commands.StartGameCommand;
import nl.infosupport.demo.game.events.GameStartedEvent;
import nl.infosupport.demo.game.events.MoveMadeEvent;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.exceptions.PolicyViolatedException;
import nl.infosupport.demo.game.models.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateRoot;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@AggregateRoot
public class GameAggregate {

    @AggregateIdentifier
    private String id;
    private Board board;
    private GameState gameState;

    private Player whitePlayer;
    private Player blackPlayer;

    public GameAggregate() {
        // Axon requires no args constructor
    }

    @CommandHandler
    @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(StartGameCommand command) {
        final Player player1 = new Player(command.getWhitePlayer(), ChessColor.WHITE);
        final Player player2 = new Player(command.getBlackPlayer(), ChessColor.BLACK);

        apply(new GameStartedEvent(command.getId(), BoardCreator.singlePawBoard(), GameState.STARTED, player1, player2));
    }

    @CommandHandler
    public void handle(MakeMoveCommand command) throws PolicyViolatedException {
        final Move move = new Move(command.getPiece(), command.getStartPosition(), command.getEndPosition());
        try {
            move.make(board);
        } catch (IllegalChessMoveException e) {
            throw new PolicyViolatedException("Unable to make move", e);
        }

        apply(new MoveMadeEvent(this.id, move));
    }

    @EventSourcingHandler
    public void handle(GameStartedEvent event) {
        id = event.getId();
        board = event.getBoard();
        gameState = event.getGameState();
        whitePlayer = event.getWhitePlayer();
        blackPlayer = event.getBlackPlayer();
    }

    @EventSourcingHandler
    public void handle(MoveMadeEvent event) {
        board.updateBoard(event.getMove());
    }
}
