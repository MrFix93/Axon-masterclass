package nl.infosupport.demo.game.printer;

import nl.infosupport.demo.game.models.Piece;

public interface PiecePrinter<T> {
    T print(Piece piece);
}
