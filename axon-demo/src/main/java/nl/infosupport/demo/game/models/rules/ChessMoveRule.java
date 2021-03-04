package nl.infosupport.demo.game.models.rules;

import nl.infosupport.demo.game.models.Board;
import nl.infosupport.demo.game.models.Move;

public interface ChessMoveRule {
    boolean isValid(Move move, Board board);

    String getFailureReason();
}
