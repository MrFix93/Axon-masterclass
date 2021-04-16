package nl.infosupport.demo.game;

import nl.infosupport.demo.game.command.models.Board;
import nl.infosupport.demo.game.command.models.BoardCreator;
import nl.infosupport.demo.game.command.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.command.printer.FancyBoardPrinter;
import org.junit.jupiter.api.Test;

class SimpleBoardPrinterTest {

    @Test
    void test() {
        final Board board = BoardCreator.fullBoard();

        final ConsoleBoardPrinter boardPrinter = new FancyBoardPrinter(System.out);
        boardPrinter.print(board);
    }

}
