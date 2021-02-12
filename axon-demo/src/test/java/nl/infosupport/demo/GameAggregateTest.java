package nl.infosupport.demo;

import nl.infosupport.demo.game.GameAggregate;
import nl.infosupport.demo.game.commands.MakeMoveCommand;
import nl.infosupport.demo.game.commands.StartGameCommand;
import nl.infosupport.demo.game.events.GameStartedEvent;
import nl.infosupport.demo.game.events.MoveMadeEvent;
import nl.infosupport.demo.game.models.*;
import nl.infosupport.demo.game.models.pieces.Paw;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
                                BoardCreator.singlePawBoard(),
                                GameState.STARTED,
                                new Player("Peter", ChessColor.WHITE),
                                new Player("Raymond", ChessColor.BLACK)
                        ));
    }

    @Test
    public void testMakeMove() {
        final String gameId = "gameId";

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.singlePawBoard(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                new Player("Raymond", ChessColor.BLACK)
        );

        final MakeMoveCommand moveCommand = new MakeMoveCommand(gameId, new Paw(ChessColor.WHITE), new Square(File.a, 1), new Square(File.a, 2), false);

        fixture.given(gameStartedEvent)
                .when(moveCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Paw(ChessColor.WHITE), new Square(File.a, 1), new Square(File.a, 2), Move.MoveType.NORMAL)));
    }

    @Test
    public void testMakeMove_sequential() {
        final String gameId = "gameId";

        final GameStartedEvent gameStartedEvent = new GameStartedEvent(gameId,
                BoardCreator.singlePawBoard(),
                GameState.STARTED,
                new Player("Peter", ChessColor.WHITE),
                new Player("Raymond", ChessColor.BLACK)
        );

        final MakeMoveCommand a3 = new MakeMoveCommand(gameId, new Paw(ChessColor.WHITE), new Square(File.a, 2), new Square(File.a, 3), false);

        final MoveMadeEvent a2 = new MoveMadeEvent(gameId, new Move(new Paw(ChessColor.WHITE), new Square(File.a, 1), new Square(File.a, 2), Move.MoveType.NORMAL));

        fixture.given(gameStartedEvent, a2)
                .when(a3)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new MoveMadeEvent(gameId, new Move(new Paw(ChessColor.WHITE), new Square(File.a, 2), new Square(File.a, 3), Move.MoveType.NORMAL)));
    }
}
