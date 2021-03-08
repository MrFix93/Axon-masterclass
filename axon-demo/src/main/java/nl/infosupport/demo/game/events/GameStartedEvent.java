package nl.infosupport.demo.game.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.ChessColor;
import nl.infosupport.demo.game.models.GameState;
import nl.infosupport.demo.game.models.Player;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GameStartedEvent extends Event {
    private final Board board;
    private final GameState gameState;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final ChessColor firstMove;

    public GameStartedEvent(String id, Board board, GameState gameState, Player whitePlayer, Player blackPlayer, ChessColor firstMove) {
        super(id);
        this.board = board;
        this.gameState = gameState;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.firstMove = firstMove;
    }
}
