package nl.infosupport.demo.game;

import nl.infosupport.demo.game.command.commands.EndGameCommand;
import nl.infosupport.demo.game.command.commands.MakeMoveCommand;
import nl.infosupport.demo.game.command.commands.StartGameCommand;
import nl.infosupport.demo.game.command.events.GameEndedEvent;
import nl.infosupport.demo.game.command.events.GameStartedEvent;
import nl.infosupport.demo.game.command.events.MoveMadeEvent;
import nl.infosupport.demo.game.command.exceptions.IllegalBoardSquareException;
import nl.infosupport.demo.game.command.models.*;
import nl.infosupport.demo.game.command.models.pieces.King;
import nl.infosupport.demo.game.command.models.pieces.Pawn;
import nl.infosupport.demo.game.command.models.pieces.Queen;
import nl.infosupport.demo.game.command.models.pieces.Rook;
import nl.infosupport.demo.game.command.utils.Squares;
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
                                new Player("Raymond", ChessColor.BLACK),
                                ChessColor.WHITE));
    }

    @Test
    public void testMakeMove() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final Player peter = new Player("Peter", ChessColor.WHITE);
        final Player raymond = new Player("Raymond", ChessColor.BLACK);

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                peter,
                raymond,
                ChessColor.WHITE);

        final MakeMoveCommand moveCommand = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a2"), Squares.from("a3"), false, peter);

        fixture.given(gameStartedEvent)
                .when(moveCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a2", "a3")));
    }

    @Test
    public void testUnableToMakeMove_withOpponentPieces() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final Player peter = new Player("Peter", ChessColor.WHITE);
        final Player raymond = new Player("Raymond", ChessColor.BLACK);

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                peter,
                raymond,
                ChessColor.BLACK);

        final MakeMoveCommand moveCommand = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a2"), Squares.from("a3"), false, raymond);

        fixture.given(gameStartedEvent)
                .when(moveCommand)
                .expectExceptionMessage("You cannot move the pieces of your opponent")
                .expectNoEvents();
    }

    @Test
    public void testUnableToMakeMove_notInTurnWhite() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final Player peter = new Player("Peter", ChessColor.WHITE);
        final Player raymond = new Player("Raymond", ChessColor.BLACK);

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                peter,
                raymond,
                ChessColor.BLACK);

        final MakeMoveCommand moveCommand = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a2"), Squares.from("a3"), false, peter);

        fixture.given(gameStartedEvent)
                .when(moveCommand)
                .expectExceptionMessage("It's not your turn")
                .expectNoEvents();
    }

    @Test
    public void testMakeMove_sequential() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final Player peter = new Player("Peter", ChessColor.WHITE);
        final Player raymond = new Player("Raymond", ChessColor.BLACK);

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                peter,
                raymond,
                ChessColor.WHITE);

        final MoveMadeEvent a3 = new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a2", "a3"));
        final MoveMadeEvent a7 = new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.BLACK), Squares.from("a7"), Squares.from("a6")));
        final MakeMoveCommand a4 = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a3"), Squares.from("a4"), false, peter);

        fixture.given(gameStartedEvent, a3, a7)
                .when(a4)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a3", "a4")));
    }

    @Test
    public void testMakeMove_sequential_differentPieces() throws IllegalBoardSquareException {
        final String gameId = "gameId";

        final Player peter = new Player("Peter", ChessColor.WHITE);
        final Player raymond = new Player("Raymond", ChessColor.BLACK);

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.fullBoard(),
                GameState.STARTED,
                peter,
                raymond,
                ChessColor.WHITE);

        final MakeMoveCommand a4 = new MakeMoveCommand(gameId, new Pawn(ChessColor.WHITE), Squares.from("a3"), Squares.from("a4"), false, peter);
        final MoveMadeEvent a7 = new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.BLACK), Squares.from("a7"), Squares.from("a6")));
        final MoveMadeEvent a3 = new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a2", "a3"));

        fixture.given(gameStartedEvent, a3, a7)
                .when(a4)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Pawn(ChessColor.WHITE), "a3", "a4")));
    }

    @Test
    public void testMakeMove_checkMate() throws IllegalBoardSquareException {
        final String gameId = "gameId";
        final Player raymond = new Player("Raymond", ChessColor.BLACK);

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                new BoardCreator.Builder()
                        .with(Squares.from("a1"), King.white())
                        .with(Squares.from("c2"), Queen.black())
                        .with(Squares.from("d8"), Rook.black())
                        .build(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                raymond,
                ChessColor.BLACK);

        final MakeMoveCommand rd1 = new MakeMoveCommand(gameId, Rook.black(), Squares.from("d8"), Squares.from("d1"), false, raymond);

        fixture.given(gameStartedEvent)
                .when(rd1)
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new MoveMadeEvent(gameId, new Move(Rook.black(), "d8", "d1")),
                        new GameEndedEvent(gameId, List.of(
                                new Move(Rook.black(), "d8", "d1")
                        ), EndGameCommand.EndingReason.BLACK_WON, raymond)
                );
    }
}
