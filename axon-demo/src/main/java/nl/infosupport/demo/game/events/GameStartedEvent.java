package nl.infosupport.demo.game.events;

import lombok.*;
import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.GameState;
import nl.infosupport.demo.game.models.Player;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GameStartedEvent extends Event {
    Board board;
    GameState gameState;
    Player whitePlayer;
    Player blackPlayer;

    public GameStartedEvent(String id, Board board, GameState gameState, Player whitePlayer, Player blackPlayer) {
        super(id);
        this.board = board;
        this.gameState = gameState;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }
}
