package nl.infosupport.demo.game;

import nl.infosupport.demo.game.commands.RegisterUserCommand;
import nl.infosupport.demo.game.events.UserRegisteredEvent;
import nl.infosupport.demo.game.exceptions.PolicyViolatedException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String email;
    private String name;
    private String country;
    private String shortBio;

    @CommandHandler
    @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException {
        if (email != null) {
            throw new PolicyViolatedException("Email already exists");
        }

        final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(registerUserCommand.getId(), registerUserCommand.getUser());

        apply(userRegisteredEvent);
    }

    @EventSourcingHandler
    void handle(UserRegisteredEvent userRegisteredEvent) {
        this.email = userRegisteredEvent.getId();
        this.name = userRegisteredEvent.getUser().getName();
        this.country = userRegisteredEvent.getUser().getCountry();
        this.shortBio = userRegisteredEvent.getUser().getShortBio();
    }

}
