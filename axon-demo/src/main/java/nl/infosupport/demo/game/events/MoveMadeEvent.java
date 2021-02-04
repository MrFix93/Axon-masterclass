package nl.infosupport.demo.game.events;

import lombok.Getter;
import nl.infosupport.demo.game.models.Move;

import java.util.UUID;

public class MoveMadeEvent extends Event {
    @Getter
    private final Move move;

    public MoveMadeEvent(UUID id, Move move) {
        super(id);
        this.move = move;
    }
}
