package nl.infosupport.demo.game.exceptions;

import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.Move;
import nl.infosupport.demo.game.printer.ConsoleBoardPrinter;
import nl.infosupport.demo.game.printer.FancyBoardPrinter;

public class IllegalChessMoveException extends Exception {
    private static final ConsoleBoardPrinter printer = new FancyBoardPrinter(System.out);
    private final Move move;
    private final Board board;

    public IllegalChessMoveException(String message, Move move, Board board) {
        super(message);
        this.move = move;
        this.board = board;
    }

    @Override
    public String getMessage() {
        final String before = printer.getRawString(board);
        board.commit(move);
        final String after = printer.getRawString(board);

        return String.format("%s with move %s \n" +
                        "Before: %s \n" +
                        "After: %s",
                super.getMessage(),
                move.toString(),
                before,
                after);
    }
}
