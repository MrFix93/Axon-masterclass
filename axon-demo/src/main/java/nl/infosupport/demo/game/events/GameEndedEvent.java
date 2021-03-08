package nl.infosupport.demo.game.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.infosupport.demo.game.commands.EndGameCommand;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.models.Player;

import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GameEndedEvent extends Event {
    private final EndGameCommand.EndingReason reason;
    private final List<Move> movesMade;
    private final Player winner;

    public GameEndedEvent(String id, List<Move> movesMade, EndGameCommand.EndingReason reason, Player winner) {
        super(id);
        this.reason = reason;
        this.movesMade = movesMade;
        this.winner = winner;
    }
}
