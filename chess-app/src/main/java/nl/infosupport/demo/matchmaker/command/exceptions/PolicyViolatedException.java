package nl.infosupport.demo.matchmaker.command.exceptions;

public class PolicyViolatedException extends Exception {
    public PolicyViolatedException(String message, Throwable e) {
    }
    public PolicyViolatedException(String message) {
        super(message);
    }
}
