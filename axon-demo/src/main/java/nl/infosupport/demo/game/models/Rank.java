package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.infosupport.demo.game.exceptions.IllegalBoardSquareException;

import java.util.Optional;

@EqualsAndHashCode
@ToString
public class Rank implements Comparable<Rank> {
    public static Rank one;
    public static Rank two;
    public static Rank three;
    public static Rank four;
    public static Rank five;
    public static Rank six;
    public static Rank seven;
    public static Rank eight;

    static {
        try {
            one = new Rank(1);
            two = new Rank(2);
            three = new Rank(3);
            four = new Rank(4);
            five = new Rank(5);
            six = new Rank(6);
            seven = new Rank(7);
            eight = new Rank(8);
        } catch (IllegalBoardSquareException e) {
            e.printStackTrace();
        }
    }

    private final int value;

    public Rank(int value) throws IllegalBoardSquareException {
        if (value < 0 || value > 8) {
            throw new IllegalBoardSquareException("Illegal rank");
        }

        this.value = value;
    }

    public static Rank max() {
        return Rank.eight;
    }

    public static Rank min() {
        return Rank.one;
    }

    public static Rank from(char rankIntValue) {
        return new Rank(rankIntValue);
    }

    public Optional<Rank> plus() throws IllegalBoardSquareException {
        int targetRank = value + 1;

        if (targetRank > 8) {
            return Optional.empty();
        } else {
            return Optional.of(new Rank(targetRank));
        }
    }

    public Optional<Rank> minus() throws IllegalBoardSquareException {
        int targetRank = value - 1;

        if (targetRank < 1) {
            return Optional.empty();
        } else {
            return Optional.of(new Rank(targetRank));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(Rank o) {
        if (this.value == o.value) {
            return 0;
        }

        return this.value > o.value ? 1 : -1;
    }

    public int getOrdinal() {
        return this.value;
    }
}
