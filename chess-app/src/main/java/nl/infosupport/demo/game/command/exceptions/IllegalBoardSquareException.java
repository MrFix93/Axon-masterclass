package nl.infosupport.demo.game.command.exceptions;

public class IllegalBoardSquareException extends RuntimeException {
    public IllegalBoardSquareException(String message) {
        super(message);
    }
}
