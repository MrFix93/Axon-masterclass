package nl.infosupport.demo.game.command.models;

import lombok.Value;
import nl.infosupport.demo.game.command.utils.Path;
import nl.infosupport.demo.game.command.utils.Squares;

import java.util.Arrays;
import java.util.List;

@Value
public class Move {
    Piece piece;
    Square startSquare;
    Square endSquare;

    public enum MoveType {
        CAPTURE,
        NORMAL
    }

    public Move(Piece piece, Square startSquare, Square endSquare) {
        this.piece = piece;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    public Move(Piece piece, String... patterns) {
        final List<Square> squares = Squares.asList(patterns);
        if (squares.size() != 2) {
            throw new IllegalArgumentException("Unable to instantiate move from patterns " + Arrays.toString(patterns));
        }

        this.piece = piece;
        this.startSquare = squares.get(0);
        this.endSquare = squares.get(1);
    }

    public List<Square> getPath() {
        final List<Square> travelingPath = Path.constructFrom(this);
        travelingPath.remove(this.getStartSquare());
        travelingPath.remove(this.getEndSquare());

        return travelingPath;
    }
}
