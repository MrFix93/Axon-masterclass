package nl.infosupport.demo.matchmaker.query;

import nl.infosupport.demo.matchmaker.MatchMakerAggregate;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.events.InviteUpdatedEvent;
import nl.infosupport.demo.matchmaker.events.InviteSentEvent;
import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.services.InviteViewService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class InviteProjectorTest {

    @MockBean
    private InviteViewService inviteViewService;
    @Autowired
    private InviteProjector inviteProjector;

    @Test
    void testInviteSentEvent() {
        //Given
        final nl.infosupport.demo.matchmaker.command.commandmodels.Invite invite = new nl.infosupport.demo.matchmaker.command.commandmodels.Invite("Test", "test@email.com", InviteStatus.PENDING);
        final InviteSentEvent inviteSentEvent = new InviteSentEvent(MatchMakerAggregate.ID, invite);

        final ArgumentCaptor<Invite> captor = ArgumentCaptor.forClass(Invite.class);

        //When
        inviteProjector.handle(inviteSentEvent);

        //Then
        Mockito.verify(inviteViewService, times(1)).createOrUpdate(captor.capture());
        final Invite savedInvite = captor.getValue();
        assertThat(savedInvite.getPlayer1()).isEqualTo(inviteSentEvent.getInvite().getPlayer1());
        assertThat(savedInvite.getPlayer2()).isEqualTo(inviteSentEvent.getInvite().getPlayer2());
        assertThat(savedInvite.getStatus()).isEqualTo(inviteSentEvent.getInvite().getStatus().name());
    }

    @Test
    void testInviteAcceptedDeclinedEvent() {
        //Given
        final Invite invite = new Invite("id", "player1", "player2", "PENDING");
        when(inviteViewService.findByPlayer1AndPlayer2AndStatus(anyString(), anyString(), anyString())).thenReturn(invite);
        final ArgumentCaptor<Invite> captor = ArgumentCaptor.forClass(Invite.class);

        final nl.infosupport.demo.matchmaker.command.commandmodels.Invite acceptedInvite = new nl.infosupport.demo.matchmaker.command.commandmodels.Invite("player1", "player2", InviteStatus.ACCEPTED);
        final InviteUpdatedEvent inviteUpdatedEvent = new InviteUpdatedEvent(MatchMakerAggregate.ID, acceptedInvite);

        //When
        inviteProjector.handle(inviteUpdatedEvent);

        //Then
        Mockito.verify(inviteViewService, times(1)).createOrUpdate(captor.capture());
        final Invite savedInvite = captor.getValue();
        assertThat(savedInvite.getPlayer1()).isEqualTo(inviteUpdatedEvent.getInvite().getPlayer1());
        assertThat(savedInvite.getPlayer2()).isEqualTo(inviteUpdatedEvent.getInvite().getPlayer2());
        assertThat(savedInvite.getStatus()).isEqualTo(inviteUpdatedEvent.getInvite().getStatus().name());
    }
}