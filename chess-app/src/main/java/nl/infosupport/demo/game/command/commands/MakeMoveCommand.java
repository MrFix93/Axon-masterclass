package nl.infosupport.demo.game.command.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.command.models.Piece;
import nl.infosupport.demo.game.command.models.Player;
import nl.infosupport.demo.game.command.models.Square;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class MakeMoveCommand extends Command {
    Piece piece;
    Square startPosition;
    Square endPosition;
    Boolean isCapture;
    Player player;

    public MakeMoveCommand(String id, Piece piece, Square startPosition, Square endPosition, Boolean isCapture, Player player) {
        super(id);
        this.piece = piece;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.isCapture = isCapture;
        this.player = player;
    }
}


