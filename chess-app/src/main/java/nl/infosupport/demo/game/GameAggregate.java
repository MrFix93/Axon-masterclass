package nl.infosupport.demo.game;

import nl.infosupport.demo.game.command.commands.EndGameCommand;
import nl.infosupport.demo.game.command.commands.MakeMoveCommand;
import nl.infosupport.demo.game.command.commands.StartGameCommand;
import nl.infosupport.demo.game.command.events.GameEndedEvent;
import nl.infosupport.demo.game.command.events.GameStartedEvent;
import nl.infosupport.demo.game.command.events.MoveMadeEvent;
import nl.infosupport.demo.game.command.exceptions.IllegalChessMoveException;
import nl.infosupport.demo.game.command.exceptions.PolicyViolatedException;
import nl.infosupport.demo.game.command.models.*;
import nl.infosupport.demo.game.command.models.rules.*;
import nl.infosupport.demo.game.command.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.command.printer.FancyBoardPrinter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

import static nl.infosupport.demo.game.command.models.GameState.FINISHED;
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
    private List<Move> movesMade;

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
    public void handle(MakeMoveCommand command) throws PolicyViolatedException {
        if (gameState != GameState.STARTED) {
            throw new PolicyViolatedException("Cannot make move when game is not started");
        }

        if (!inTurn(command.getPlayer().getColor())) {
            throw new PolicyViolatedException("It's not your turn");
        }

        if (command.getPlayer().getColor() != command.getPiece().getColor()) {
            throw new PolicyViolatedException("You cannot move the pieces of your opponent");
        }

        final Move move = new Move(command.getPiece(), command.getStartPosition(), command.getEndPosition());
        try {
            board.make(move);
        } catch (IllegalChessMoveException e) {
            throw new PolicyViolatedException("Unable to make move", e);
        }

        apply(new MoveMadeEvent(id, move));

        if (CheckMateRule.isCheckMate(board, ChessColor.WHITE)) {
            apply(new GameEndedEvent(id, movesMade, EndGameCommand.EndingReason.BLACK_WON, blackPlayer));
        }

        if (CheckMateRule.isCheckMate(board, ChessColor.BLACK)) {
            apply(new GameEndedEvent(id, movesMade, EndGameCommand.EndingReason.WHITE_WON, whitePlayer));
        }
    }

    private boolean inTurn(ChessColor color) {
        return !((movesMade.size() + 1) % 2 == 1 && color == firstMove.invert());
    }

    @EventSourcingHandler
    public void handle(GameStartedEvent event) {
        id = event.getId();
        board = event.getBoard();
        gameState = event.getGameState();
        whitePlayer = event.getWhitePlayer();
        blackPlayer = event.getBlackPlayer();
        firstMove = event.getFirstMove();

        movesMade = new ArrayList<>();

        printer.print(board);
    }

    @EventSourcingHandler
    public void handle(MoveMadeEvent event) {
        final Move move = event.getMove();
        board.commit(move);
        movesMade.add(event.getMove());

        printer.print(board);
    }

    @EventSourcingHandler
    public void handle(GameEndedEvent event) {
        gameState = FINISHED;
    }
}
