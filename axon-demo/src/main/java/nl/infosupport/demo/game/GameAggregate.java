package nl.infosupport.demo.game;

import nl.infosupport.demo.game.commands.EndGameCommand;
import nl.infosupport.demo.game.commands.MakeMoveCommand;
import nl.infosupport.demo.game.commands.StartGameCommand;
import nl.infosupport.demo.game.events.GameEndedEvent;
import nl.infosupport.demo.game.events.GameStartedEvent;
import nl.infosupport.demo.game.events.MoveMadeEvent;
import nl.infosupport.demo.game.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.exceptions.PolicyViolatedException;
import nl.infosupport.demo.game.models.*;
import nl.infosupport.demo.game.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.printer.FancyBoardPrinter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateRoot;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

import static nl.infosupport.demo.game.models.GameState.FINISHED;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@AggregateRoot
public class GameAggregate {

    @AggregateIdentifier
    private String id;
    private Board board;
    private GameState gameState;
    private final ConsoleBoardPrinter printer = new FancyBoardPrinter(System.out);

    private Player whitePlayer;
    private Player blackPlayer;
    private List<Move> movesMade;

    public GameAggregate() {
        // Axon requires no args constructor
    }

    @CommandHandler
    @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(StartGameCommand command) {
        final Player player1 = new Player(command.getWhitePlayer(), ChessColor.WHITE);
        final Player player2 = new Player(command.getBlackPlayer(), ChessColor.BLACK);

        apply(new GameStartedEvent(command.getId(), BoardCreator.fullBoard(), GameState.STARTED, player1, player2));
    }

    @CommandHandler
    public void handle(MakeMoveCommand command) throws PolicyViolatedException {
        if (gameState != GameState.STARTED) {
            throw new PolicyViolatedException("Cannot make move when game is not started");
        }

        final Move move = new Move(command.getPiece(), command.getStartPosition(), command.getEndPosition());
        try {
            move.makeAndCommit(board);
        } catch (IllegalChessMoveException e) {
            throw new PolicyViolatedException("Unable to make move", e);
        }

        apply(new MoveMadeEvent(this.id, move));

        if (board.isCheckMate(ChessColor.WHITE)) {
            apply(new GameEndedEvent(this.id, EndGameCommand.EndingReason.BLACK_WON, movesMade));
        }

        if (board.isCheckMate(ChessColor.BLACK)) {
            apply(new GameEndedEvent(this.id, EndGameCommand.EndingReason.WHITE_WON, movesMade));
        }
    }

    @EventSourcingHandler
    public void handle(GameStartedEvent event) {
        id = event.getId();
        board = event.getBoard();
        gameState = event.getGameState();
        whitePlayer = event.getWhitePlayer();
        blackPlayer = event.getBlackPlayer();
        movesMade = new ArrayList<>();

        printer.print(board);
    }

    @EventSourcingHandler
    public void handle(MoveMadeEvent event) {
        board.updateBoard(event.getMove());
        movesMade.add(event.getMove());

        printer.print(board);
    }

    @EventSourcingHandler
    public void handle(GameEndedEvent event) {
        gameState = FINISHED;
    }
}
