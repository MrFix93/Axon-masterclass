package nl.infosupport.demo.matchmaker.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public abstract class Event {
    @Getter
    @TargetAggregateIdentifier
    private final String id;

    Event() {
        this.id = UUID.randomUUID().toString();
    }

    Event(String id) {
        this.id = id;
    }
}
