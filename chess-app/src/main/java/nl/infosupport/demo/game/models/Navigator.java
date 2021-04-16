package nl.infosupport.demo.game.models;

import nl.infosupport.demo.game.exceptions.IllegalBoardSquareException;

import java.util.Optional;

public class Navigator {
    ChessColor color;

    public Navigator(ChessColor color) {
        this.color = color;
    }

    public static Navigator white() {
        return new Navigator(ChessColor.WHITE);
    }

    public static Navigator black() {
        return new Navigator(ChessColor.BLACK);
    }

    public Rank getPawnStartRank() {
        if (color.equals(ChessColor.WHITE)) {
            return Rank.two;
        } else {
            return Rank.seven;
        }
    }

    public Optional<Square> left(Square square) {
        File file = square.getFile();
        Rank rank = square.getRank();
        if (color.equals(ChessColor.WHITE)) {
            return file.minus().map(value -> new Square(value, rank));
        }

        if (color.equals(ChessColor.BLACK)) {
            return file.plus().map(value -> new Square(value, rank));
        }

        throw new IllegalBoardSquareException("Unable to get square");
    }

    public Optional<Square> up(Square square) {
        File file = square.getFile();
        Rank rank = square.getRank();
        if (color.equals(ChessColor.WHITE)) {
            return rank.plus().map(targetRank -> new Square(file, targetRank));
        }

        if (color.equals(ChessColor.BLACK)) {
            return rank.minus().map(targetRank -> new Square(file, targetRank));
        }

        throw new IllegalBoardSquareException("Unable to get square");
    }

    public Optional<Square> down(Square square) {
        File file = square.getFile();
        Rank rank = square.getRank();
        if (color.equals(ChessColor.WHITE)) {
            return rank.minus().map(targetRank -> new Square(file, targetRank));
        }

        if (color.equals(ChessColor.BLACK)) {
            return rank.plus().map(targetRank -> new Square(file, targetRank));
        }

        throw new IllegalBoardSquareException("Unable to get square");
    }

    public Optional<Square> right(Square square) {
        File file = square.getFile();
        Rank rank = square.getRank();
        if (color.equals(ChessColor.WHITE)) {
            return file.plus().map(targetFile -> new Square(targetFile, rank));
        }

        if (color.equals(ChessColor.BLACK)) {
            return file.minus().map(targetFile -> new Square(targetFile, rank));
        }

        throw new IllegalBoardSquareException("Unable to get square");
    }

    public Optional<Square> acrossForwards(Square startSquare, Direction direction) {
        final Optional<Square> up = this.up(startSquare);
        if (up.isPresent()) {
            final Square square = up.get();
            if (direction.equals(Direction.LEFT)) {
                return this.left(square);
            } else {
                return this.right(square);
            }
        }

        return Optional.empty();
    }

    public Optional<Square> acrossBackwards(Square startSquare, Direction direction) {
        final Optional<Square> down = this.down(startSquare);
        if (down.isPresent()) {
            final Square square = down.get();
            if (direction.equals(Direction.LEFT)) {
                return this.left(square);
            } else {
                return this.right(square);
            }
        }

        return Optional.empty();
    }

    public enum Direction {
        LEFT, RIGHT
    }
}
