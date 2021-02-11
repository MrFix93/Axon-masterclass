package nl.infosupport.demo.game.models;

public enum ChessColor {
    WHITE,
    BLACK;

    ChessColor invert() {
        return ChessColor.values()[this.ordinal() + 1 % 2];
    }
}
