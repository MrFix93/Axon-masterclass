package nl.infosupport.demo.game.models;

public class Tile {
    File file;
    int rank;

    public Tile(File file, int rank) throws IllegalArgumentException {
        this.file = file;

        if (rank < 0 || rank > 8) {
            throw new IllegalArgumentException("No a valid position, rank should be between 1-8");
        }

        this.rank = rank;
    }
}
