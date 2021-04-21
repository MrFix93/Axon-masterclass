package nl.infosupport.demo.matchmaker.command.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@EqualsAndHashCode
public class Command {
    @Getter(onMethod_ = {@TargetAggregateIdentifier})
    private String id;

    public Command() {

    }

    public Command(String id) {
        this.id = id;
    }
}
