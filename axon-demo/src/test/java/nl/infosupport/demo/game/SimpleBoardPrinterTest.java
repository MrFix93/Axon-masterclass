package nl.infosupport.demo.game;

import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.BoardCreator;
import nl.infosupport.demo.game.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.printer.FancyBoardPrinter;
import org.junit.jupiter.api.Test;

class SimpleBoardPrinterTest {

    @Test
    void test() {
        final Board board = BoardCreator.fullBoard();

        final ConsoleBoardPrinter boardPrinter = new FancyBoardPrinter(System.out);
        boardPrinter.print(board);
    }

}
