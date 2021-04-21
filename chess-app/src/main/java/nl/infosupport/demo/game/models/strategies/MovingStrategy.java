package nl.infosupport.demo.game.models.strategies;

import nl.infosupport.demo.game.models.Navigator;
import nl.infosupport.demo.game.models.Square;

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
