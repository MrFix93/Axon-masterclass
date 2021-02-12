package nl.infosupport.demo.game;

import nl.infosupport.demo.game.commands.RegisterUserCommand;
import nl.infosupport.demo.game.events.UserRegisteredEvent;
import nl.infosupport.demo.game.exceptions.PolicyViolatedException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String email;
    private String name;

    @CommandHandler
    public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException {
        if (email != null) {
            throw new PolicyViolatedException("Email already exists");
        }

        //todo maybe instead of uuid to String id?
        final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(UUID.fromString(registerUserCommand.getId()), registerUserCommand.getUser());

        apply(userRegisteredEvent);
    }

    @EventSourcingHandler
    void handle(UserRegisteredEvent userRegisteredEvent) {
        this.email = userRegisteredEvent.getUser().getEmail();
        this.name = userRegisteredEvent.getUser().getName();
    }

}
