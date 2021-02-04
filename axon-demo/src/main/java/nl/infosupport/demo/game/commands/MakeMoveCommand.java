package nl.infosupport.demo.game.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.Piece;
import nl.infosupport.demo.game.models.Tile;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class MakeMoveCommand extends Command {
    Piece piece;
    Tile startPosition;
    Tile endPosition;

    public MakeMoveCommand(String id, Piece piece, Tile startPosition, Tile endPosition) {
        super(id);
        this.piece = piece;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}


