package nl.infosupport.demo.game.command.utils;

import nl.infosupport.demo.game.command.models.File;
import nl.infosupport.demo.game.command.models.Rank;
import nl.infosupport.demo.game.command.models.Square;

import java.util.ArrayList;
import java.util.List;

public class Squares {
    public static Square from(String pattern) {
        if (pattern.length() != 2) {
            throw new IllegalArgumentException("Can only create Square from pattern File|Rank, for example a4");
        }

        File file = File.valueOf(pattern.substring(0, 1));
        Rank rank = new Rank(Integer.parseInt(pattern.substring(1, 2)));

        return new Square(file, rank);
    }

    public static List<Square> asList(String... patterns) {
        final ArrayList<Square> patternList = new ArrayList<>();
        for (String pattern : patterns) {
            patternList.add(from(pattern));
        }

        return patternList;
    }
}
