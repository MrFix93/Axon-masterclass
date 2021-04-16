package nl.infosupport.demo.game.command.models.strategies;

import nl.infosupport.demo.game.command.models.Navigator;
import nl.infosupport.demo.game.command.models.Square;

import java.util.List;

public interface MovingStrategy {

    List<Square> getRange(Square currentSquare);

    default List<Square> getAttackRange(Square currentSquare) {
        return getRange(currentSquare);
    }

    default Navigator getNavigator() {
        return Navigator.white();
    }

    enum RangeMode {
        SINGLE, UNLIMITED
    }
}
