package nl.infosupport.demo.matchmaker;

import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import nl.infosupport.demo.matchmaker.events.UserAddedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class MatchMakerAggregate {

    @AggregateIdentifier
    public static final String ID = "SINGLETON_MATCHMAKER_ID";

    private final Map<String, String> users = new HashMap<>();

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(AddUserCommand addUserCommand) {
        final UserAddedEvent userAddedEvent = new UserAddedEvent(addUserCommand.getId(), addUserCommand.getUser());

        apply(userAddedEvent);
    }

    @EventSourcingHandler
    public void handle(UserAddedEvent event) {
        users.put(event.getUser().getEmail(), event.getUser().getName());
    }

}
