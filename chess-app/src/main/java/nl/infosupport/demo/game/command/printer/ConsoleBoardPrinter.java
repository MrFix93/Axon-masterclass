package nl.infosupport.demo.game.command.printer;

import nl.infosupport.demo.game.command.models.Board;

public interface ConsoleBoardPrinter {
    void print(Board board);

    String getRawString(Board board);
}
