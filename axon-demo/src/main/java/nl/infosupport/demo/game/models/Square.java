package nl.infosupport.demo.game.models;

import lombok.Value;

@Value
public class Square {
    File file;
    Rank rank;

    public Square(Rank rank, File file) {
        this.file = file;
        this.rank = rank;
    }

    public Square(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public Square(File file, int rank) {
        this.file = file;
        this.rank = new Rank(rank);
    }

    public static Square of(File file, Rank rank) {
        return new Square(file, rank);
    }

    public ChessColor getColor() {
        if ((file.getOrdinal() + rank.getOrdinal()) % 2 == 0) {
            return ChessColor.BLACK;
        }

        return ChessColor.WHITE;
    }

    public Coordinate getCoordinates() {
        return new Coordinate(file.getOrdinal(), rank.getOrdinal());
    }

}
