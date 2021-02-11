package nl.infosupport.demo.game.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.infosupport.demo.game.models.Move;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MoveMadeEvent extends Event {
    @Getter
    private final Move move;

    public MoveMadeEvent(String id, Move move) {
        super(id);
        this.move = move;
    }
}
