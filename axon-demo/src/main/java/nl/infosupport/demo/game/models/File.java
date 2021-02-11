package nl.infosupport.demo.game.models;

public enum File {
    a,b,c,d,e,f,g,h;

    public File left() {
        return File.values()[this.ordinal() + 1];
    }

    public File right() {
        return File.values()[this.ordinal() - 1];
    }
}
