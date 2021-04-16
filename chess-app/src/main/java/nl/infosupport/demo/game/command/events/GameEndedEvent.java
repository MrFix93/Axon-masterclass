package nl.infosupport.demo.game.command.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.infosupport.demo.game.command.commands.EndGameCommand;
import nl.infosupport.demo.game.command.models.Move;
import nl.infosupport.demo.game.command.models.Player;

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
