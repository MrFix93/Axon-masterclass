package nl.infosupport.demo.game.command.printer;

import nl.infosupport.demo.game.command.models.Piece;

public interface PiecePrinter<T> {
    T print(Piece piece);
}
