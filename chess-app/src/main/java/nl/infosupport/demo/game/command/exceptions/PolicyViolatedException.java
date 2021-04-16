package nl.infosupport.demo.game.command.exceptions;

public class PolicyViolatedException extends Exception {
    public PolicyViolatedException(String message) {
        super(message);
    }

    public PolicyViolatedException(String message, Throwable e) {
    }
}
