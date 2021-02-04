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
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class GameAggregate {

    @AggregateIdentifier
    private UUID id;
    private Board board;
    private GameState gameState;

    private Player whitePlayer;
    private Player blackPlayer;

    @CommandHandler
    public GameAggregate(StartGameCommand command) {
        final Player player1 = new Player(command.getWhitePlayer(), ChessColor.WHITE);
        final Player player2 = new Player(command.getBlackPlayer(), ChessColor.BLACK);

        apply(new GameStartedEvent(Board.singlePawBoard(), GameState.STARTED, player1, player2));
    }

    @CommandHandler
    public GameAggregate(MakeMoveCommand command) throws PolicyViolatedException {
        final Move move = new Move(command.getPiece(), command.getStartPosition(), command.getStartPosition());
        try {
            board.move(move);
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
    public void handle(MoveMadeEvent event) throws IllegalChessMoveException {
        board.move(event.getMove());
    }
}
