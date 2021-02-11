package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Square {
    File file;
    int rank;

    public Square(File file, int rank) {
        this.file = file;

        if (rank < 0 || rank > 8) {
            throw new IllegalArgumentException("No a valid position, rank should be between 1-8");
        }

        this.rank = rank;
    }
}
