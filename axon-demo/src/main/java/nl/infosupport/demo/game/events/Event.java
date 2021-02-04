package nl.infosupport.demo.game.events;

import lombok.Getter;

import java.util.UUID;

public class Event {
    @Getter
    private UUID id;

    public Event() {
        this.id = UUID.randomUUID();
    }

    public Event(UUID id) {
        this.id = id;
    }
}
