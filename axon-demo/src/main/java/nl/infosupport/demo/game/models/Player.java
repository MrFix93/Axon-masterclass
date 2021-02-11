package nl.infosupport.demo.game.models;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Player {
    String name;
    ChessColor color;

    public Player(String name, ChessColor color) {
        this.name = name;
        this.color = color;
    }
}
