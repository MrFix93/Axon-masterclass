package nl.infosupport.demo.game.command.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.infosupport.demo.game.command.models.Move;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MoveMadeEvent extends Event {
    private final Move move;

    public MoveMadeEvent(String id, Move move) {
        super(id);
        this.move = move;
    }
}