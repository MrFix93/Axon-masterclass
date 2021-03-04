package nl.infosupport.demo.game.models.strategies;

import nl.infosupport.demo.game.models.File;
import nl.infosupport.demo.game.models.Rank;
import nl.infosupport.demo.game.models.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KnightJumpMovingStrategy implements MovingStrategy {

    @Override
    public List<Square> getRange(Square currentSquare) {
        List<Square> range = new ArrayList<>();

        getLeft(currentSquare, range);
        getRight(currentSquare, range);
        getUp(currentSquare, range);
        getDown(currentSquare, range);

        return range;
    }

    public static boolean isKnightJump(List<Rank> ranks, List<File> files) {
        return (ranks.size() == 2 && files.size() == 3) || (ranks.size() == 3 && files.size() == 2);
    }

    private void getDown(Square currentSquare, List<Square> range) {
        final Optional<Square> down = getNavigator().down(currentSquare);

        if (down.isPresent()) {
            final Optional<Square> downTwo = getNavigator().down(down.get());
            if (downTwo.isPresent()) {
                final Square square = downTwo.get();
                getNavigator().left(square).ifPresent(range::add);
                getNavigator().right(square).ifPresent(range::add);
            }
        }
    }

    private void getUp(Square currentSquare, List<Square> range) {
        final Optional<Square> up = getNavigator().up(currentSquare);

        if (up.isPresent()) {
            final Optional<Square> upTwo = getNavigator().up(up.get());
            if (upTwo.isPresent()) {
                final Square square = upTwo.get();
                getNavigator().left(square).ifPresent(range::add);
                getNavigator().right(square).ifPresent(range::add);
            }
        }
    }

    private void getRight(Square currentSquare, List<Square> range) {
        final Optional<Square> right = getNavigator().right(currentSquare);

        if (right.isPresent()) {
            final Optional<Square> rightTwo = getNavigator().right(right.get());
            if (rightTwo.isPresent()) {
                final Square square = rightTwo.get();
                getNavigator().up(square).ifPresent(range::add);
                getNavigator().down(square).ifPresent(range::add);
            }
        }
    }

    private void getLeft(Square currentSquare, List<Square> range) {
        final Optional<Square> left = getNavigator().left(currentSquare);

        if (left.isPresent()) {
            final Optional<Square> leftTwo = getNavigator().left(left.get());
            if (leftTwo.isPresent()) {
                final Square square = leftTwo.get();
                getNavigator().up(square).ifPresent(range::add);
                getNavigator().down(square).ifPresent(range::add);
            }
        }
    }
}
