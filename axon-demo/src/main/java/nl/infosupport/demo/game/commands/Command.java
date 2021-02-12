package nl.infosupport.demo.game.commands;

import lombok.Getter;

public class Command {
    @Getter
    private String id;

    public Command() {

    }

    public Command(String id) {
        this.id = id;
    }
}
