package nl.infosupport.demo.game.command.models.strategies;

import nl.infosupport.demo.game.command.models.File;
import nl.infosupport.demo.game.command.models.Square;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class HorizontalMovingStrategy implements MovingStrategy {

    private final RangeMode rangeMode;

    public HorizontalMovingStrategy() {
        this.rangeMode = RangeMode.UNLIMITED;
    }

    public HorizontalMovingStrategy(RangeMode rangeMode) {
        this.rangeMode = rangeMode;
    }

    @Override
    public List<Square> getRange(Square currentSquare) {
        if (rangeMode == RangeMode.UNLIMITED) {
            return getUnlimitedRange(currentSquare);
        } else {
            return getSingleStepRange(currentSquare);
        }
    }

    private List<Square> getSingleStepRange(Square currentSquare) {
        List<Square> files = new ArrayList<>();
        getNavigator().left(currentSquare).ifPresent(files::add);
        getNavigator().right(currentSquare).ifPresent(files::add);
        return files;
    }

    private List<Square> getUnlimitedRange(Square currentSquare) {
        final EnumSet<File> files = EnumSet.range(File.min(), File.max());

        return files.stream()
                .map(file -> new Square(file, currentSquare.getRank()))
                .filter(square -> !square.equals(currentSquare))
                .collect(Collectors.toList());
    }
}
