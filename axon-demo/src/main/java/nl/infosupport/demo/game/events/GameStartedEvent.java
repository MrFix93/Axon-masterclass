package nl.infosupport.demo.game.events;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.GameState;
import nl.infosupport.demo.game.models.Player;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Value
public class GameStartedEvent extends Event {
    Board board;
    GameState gameState;
    Player whitePlayer;
    Player blackPlayer;

    public GameStartedEvent(Board board, GameState gameState, Player whitePlayer, Player blackPlayer) {
        super();
        this.board = board;
        this.gameState = gameState;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }
}
