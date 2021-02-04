package nl.infosupport.demo.game.exceptions;

import nl.infosupport.demo.game.models.Move;

public class IllegalChessMoveException extends Exception {
    Move move;
    public IllegalChessMoveException(String message, Move move) {
        super(message);
        this.move = move;
    }
}
