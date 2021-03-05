package nl.infosupport.demo.matchmaker;

import nl.infosupport.demo.matchmaker.command.commandmodels.Invite;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.command.commands.AcceptDeclineInviteCommand;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import nl.infosupport.demo.matchmaker.command.commands.SendInviteCommand;
import nl.infosupport.demo.matchmaker.command.exceptions.PolicyViolatedException;
import nl.infosupport.demo.matchmaker.events.InviteUpdatedEvent;
import nl.infosupport.demo.matchmaker.events.InviteSentEvent;
import nl.infosupport.demo.matchmaker.events.UserAddedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.*;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class MatchMakerAggregate {

    @AggregateIdentifier
    public static final String ID = "SINGLETON_MATCHMAKER_ID";

    private final Map<String, String> users = new HashMap<>();
    private final List<Invite> invites = new ArrayList<>();

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(AddUserCommand addUserCommand) {
        System.out.println("add user command");
        final UserAddedEvent userAddedEvent = new UserAddedEvent(addUserCommand.getId(), addUserCommand.getUser());

        apply(userAddedEvent);
    }

    @CommandHandler
    public void handle(SendInviteCommand sendInviteCommand) throws PolicyViolatedException {
        System.out.println("send invite command");
        final String player1 = users.get(sendInviteCommand.getPlayer1());
        final String player2 = users.get(sendInviteCommand.getPlayer2());

        if (player1 == null) {
            throw new PolicyViolatedException(String.format("User: %s does not exist.", sendInviteCommand.getPlayer1()));
        }

        if (player2 == null) {
            throw new PolicyViolatedException(String.format("User: %s does not exist.", sendInviteCommand.getPlayer2()));
        }

        final Invite invite = new Invite(sendInviteCommand.getPlayer1(), sendInviteCommand.getPlayer2(), InviteStatus.PENDING);
        final InviteSentEvent inviteSentEvent = new InviteSentEvent(ID, invite);

        apply(inviteSentEvent);
    }

    @CommandHandler
    public void handle(AcceptDeclineInviteCommand acceptDeclineInviteCommand) throws PolicyViolatedException {
        System.out.println("accept decline command");
        final Optional<Invite> optionalOpenInvite = invites.stream()
                .filter(invite -> isOpenInvite(invite, acceptDeclineInviteCommand.getPlayer1(), acceptDeclineInviteCommand.getPlayer2()))
                .findFirst();

        if (optionalOpenInvite.isEmpty()) {
            throw new PolicyViolatedException(String.format("No pending invite found for player1: %s and player2: %s", acceptDeclineInviteCommand.getPlayer1(), acceptDeclineInviteCommand.getPlayer2()));
        }

        final Invite openInvite = optionalOpenInvite.get();
        final Invite acceptedOrDeclinedInvite = openInvite.withStatus(acceptDeclineInviteCommand.getInviteStatus());

        final InviteUpdatedEvent inviteUpdatedEvent = new InviteUpdatedEvent(ID, acceptedOrDeclinedInvite);

        apply(inviteUpdatedEvent);
    }

    @EventSourcingHandler
    public void handle(UserAddedEvent event) {
        users.put(event.getUser().getEmail(), event.getUser().getName());
    }

    @EventSourcingHandler
    public void handle(InviteSentEvent event) {
        invites.add(event.getInvite());
    }

    @EventSourcingHandler
    public void handle(InviteUpdatedEvent event) {
        final Invite openInvite = invites.stream()
                .filter(invite -> isOpenInvite(invite, event.getInvite().getPlayer1(), event.getInvite().getPlayer2()))
                .findFirst()
                .get(); //.get() can be called immediately because the openInvite present check is already done in the command handler

        final int index = invites.indexOf(openInvite);

        invites.add(index, event.getInvite());
    }

    private boolean isOpenInvite(Invite invite, String player1, String player2) {
        return invite.getPlayer1().equals(player1) &&
                invite.getPlayer2().equals(player2) &&
                invite.getStatus().equals(InviteStatus.PENDING);
    }
}
