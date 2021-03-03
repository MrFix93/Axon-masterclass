package nl.infosupport.demo.game.models;

import java.util.Optional;

public enum File {
    a, b, c, d, e, f, g, h;

    public static File min() {
        return File.a;
    }

    public static File max() {
        return File.h;
    }

    public Optional<File> plus() {
        final int targetFile = this.ordinal() + 1;
        if (targetFile >= values().length) {
            return Optional.empty();
        }
        return Optional.of(File.values()[targetFile]);
    }

    public Optional<File> minus() {
        final int targetFile = this.ordinal() - 1;
        if (targetFile < 0) {
            return Optional.empty();
        }
        return Optional.of(File.values()[targetFile]);
    }

    public int getOrdinal() {
        return this.ordinal() + 1;
    }

}
