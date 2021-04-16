package nl.infosupport.demo.game.command.models.rules;

import nl.infosupport.demo.game.command.models.Board;
import nl.infosupport.demo.game.command.models.Move;

public interface ChessMoveRule {
    boolean isValid(Move move, Board board);

    String getFailureReason();
}
