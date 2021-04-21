package nl.infosupport.demo.game.handlers;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.command.commandmodels.Invite;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.events.InviteUpdatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class MatchMakerEventHandler {

    private CommandGateway commandGateway;

    @EventHandler
    public void handle(InviteUpdatedEvent event) {
        final Invite invite = event.getInvite();

        if (invite.getStatus().equals(InviteStatus.ACCEPTED)) {
            invite.getPlayer1();
            invite.getPlayer2();
        }
    }
}
