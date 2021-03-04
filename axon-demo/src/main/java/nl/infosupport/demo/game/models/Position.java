package nl.infosupport.demo.game.models;

import lombok.Value;

@Value(staticConstructor = "of")
public class Position {
    Square square;
    Piece piece;
}
