package nl.infosupport.demo.game;

import nl.infosupport.demo.game.commands.EndGameCommand;
import nl.infosupport.demo.game.commands.MakeMoveCommand;
import nl.infosupport.demo.game.commands.StartGameCommand;
import nl.infosupport.demo.game.events.GameEndedEvent;
import nl.infosupport.demo.game.events.GameStartedEvent;
import nl.infosupport.demo.game.events.MoveMadeEvent;
import nl.infosupport.demo.game.exceptions.IllegalBoardSquareException;
import nl.infosupport.demo.game.models.*;
import nl.infosupport.demo.game.models.pieces.King;
import nl.infosupport.demo.game.models.pieces.Pawn;
import nl.infosupport.demo.game.models.pieces.Queen;
import nl.infosupport.demo.game.models.pieces.Rook;
import nl.infosupport.demo.game.utils.Squares;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GameAggregateTest {

    private AggregateTestFixture<GameAggregate> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(GameAggregate.class);
    }

    @Test
    public void testStartNewGame() {
        final String gameId = "gameId";

        fixture.givenNoPriorActivity()
                .when(new StartGameCommand(gameId, "Peter", "Raymond"))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new GameStartedEvent(gameId,
                                BoardCreator.fullBoard(),
                                GameState.STARTED,
                                new Player("Peter", ChessColor.WHITE),
                                new Player("Raymond", ChessColor.BLACK)
                        ));
    }

    @Test
    public void testMakeMove() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                new Player("Raymond", ChessColor.BLACK)
        );

        final MakeMoveCommand moveCommand = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a2"), Squares.from("a3"), false);

        fixture.given(gameStartedEvent)
                .when(moveCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a2", "a3")));
    }

    @Test
    public void testMakeMove_sequential() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                new Player("Raymond", ChessColor.BLACK)
        );

        final MakeMoveCommand a4 = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a3"), Squares.from("a4"), false);
        final MoveMadeEvent a3 = new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a2", "a3"));

        fixture.given(gameStartedEvent, a3)
                .when(a4)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a3", "a4")));
    }

    @Test
    public void testMakeMove_sequential_differentPieces() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                new Player("Raymond", ChessColor.BLACK)
        );

        final MakeMoveCommand a4 = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a3"), Squares.from("a4"), false);
        final MoveMadeEvent a3 = new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a2", "a3"));

        fixture.given(gameStartedEvent, a3)
                .when(a4)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a3", "a4")));
    }

    @Test
    public void testMakeMove_checkMate() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                new BoardCreator.Builder()
                        .with(Squares.from("a1"), King.white())
                        .with(Squares.from("c2"), Queen.black())
                        .with(Squares.from("d8"), Rook.black())
                        .build(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                new Player("Raymond", ChessColor.BLACK)
        );

        final MakeMoveCommand rd1 = new MakeMoveCommand(gameId, Rook.black(), Squares.from("d8"), Squares.from("d1"), false);

        fixture.given(gameStartedEvent)
                .when(rd1)
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new MoveMadeEvent(gameId, new Move(Rook.black(), "d8", "d1")),
                        new GameEndedEvent(gameId, EndGameCommand.EndingReason.BLACK_WON, List.of(
                                new Move(Rook.black(), "d8", "d1")
                        ))
                );
    }
}
