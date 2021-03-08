package nl.infosupport.demo.game.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.Move;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class EndGameCommand extends Command {
    EndingReason reason;

    public EndGameCommand(String id, EndingReason reason, List<Move> movesMade) {
        super(id);
        this.reason = reason;
    }

    public enum EndingReason {
        WHITE_RESIGNED,
        BLACK_RESIGNED,
        WHITE_WON,
        BLACK_WON,
        REMISE
    }
}