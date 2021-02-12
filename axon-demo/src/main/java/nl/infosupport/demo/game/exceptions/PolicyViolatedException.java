package nl.infosupport.demo.game.exceptions;

public class PolicyViolatedException extends Exception {
    public PolicyViolatedException(String message, Throwable e) {
    }
    public PolicyViolatedException(String message) {
        super(message);
    }
}
