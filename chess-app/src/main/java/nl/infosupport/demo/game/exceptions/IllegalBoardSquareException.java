package nl.infosupport.demo.game.exceptions;

public class IllegalBoardSquareException extends RuntimeException {
    public IllegalBoardSquareException(String message) {
        super(message);
    }
}
