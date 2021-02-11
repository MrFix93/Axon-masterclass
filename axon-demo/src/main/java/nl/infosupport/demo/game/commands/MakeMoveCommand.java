package nl.infosupport.demo.game.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.Square;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class MakeMoveCommand extends Command {
    Piece piece;
    Square startPosition;
    Square endPosition;
    Boolean isCapture;

    public MakeMoveCommand(String id, Piece piece, Square startPosition, Square endPosition, Boolean isCapture) {
        super(id);
        this.piece = piece;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.isCapture = isCapture;
    }
}


