package nl.infosupport.demo.matchmaker.query.services;

import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.repositories.InviteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteViewServiceTest {

    @Mock
    private InviteRepository inviteRepository;

    @InjectMocks
    private InviteViewService inviteViewService;

    @Test
    void testFindAll() {
        //Given
        final Invite invite = new Invite("player1", "player2", "PENDING");
        when(inviteRepository.findAll()).thenReturn(List.of(invite));

        //When
        inviteViewService.findAll();

        //Then
        verify(inviteRepository, times(1)).findAll();
    }

    @Test
    void testCreateOrUpdate() {
        //Given
        final Invite invite = new Invite("player1", "player2", "PENDING");

        //When
        inviteViewService.createOrUpdate(invite);

        //Then
        verify(inviteRepository, times(1)).save(invite);
    }

    @Test
    void testFindByPlayer1AndPlayer2AndStatus() {
        //Given
        final Invite invite = new Invite("player1", "player2", "PENDING");
        when(inviteRepository.findByPlayer1AndPlayer2AndStatus(anyString(), anyString(), anyString())).thenReturn(List.of(invite));

        //When
        final Invite inviteByPlayer1AndPlayer2AndStatus = inviteViewService.findByPlayer1AndPlayer2AndStatus(invite.getPlayer1(), invite.getPlayer2(), invite.getStatus());

        //Then
        assertThat(inviteByPlayer1AndPlayer2AndStatus).isNotNull();
        verify(inviteRepository, times(1)).findByPlayer1AndPlayer2AndStatus(anyString(), anyString(), anyString());
    }
}