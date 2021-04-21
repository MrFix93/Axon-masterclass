package nl.infosupport.demo.matchmaker.query;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.events.InviteSentEvent;
import nl.infosupport.demo.matchmaker.events.InviteUpdatedEvent;
import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.services.InviteViewService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class InviteProjector {

    private final String PENDING_INVITE_STATUS = "PENDING";
    private final InviteViewService inviteViewService;

    @EventHandler
    public void handle(InviteSentEvent event) {
        final Invite invite = new Invite(event.getInvite().getPlayer1(), event.getInvite().getPlayer2(), event.getInvite().getStatus().name());

        inviteViewService.createOrUpdate(invite);
    }

    @EventHandler
    public void handle(InviteUpdatedEvent event) {
        final Invite inviteByPlayer1AndPlayer2AndStatus = inviteViewService.findByPlayer1AndPlayer2AndStatus(event.getInvite().getPlayer1(), event.getInvite().getPlayer2(), PENDING_INVITE_STATUS);

        inviteByPlayer1AndPlayer2AndStatus.setStatus(event.getInvite().getStatus().name());

        inviteViewService.createOrUpdate(inviteByPlayer1AndPlayer2AndStatus);
    }
}
