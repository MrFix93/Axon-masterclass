package nl.infosupport.demo.matchmaker.query;

import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.services.InviteViewService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class InviteQueryhandlerTest {

    @Autowired
    private InviteQueryhandler inviteQueryhandler;
    @MockBean
    private InviteViewService inviteViewService;

    @Test
    void testFindAllInvites() {
        //Given
        final Invite invite = new Invite("id", "Peter", "Raymond", "PENDING");
        when(inviteViewService.findAll()).thenReturn(List.of(invite));

        //When
        final List<Invite> foundInvites = inviteQueryhandler.findAll();

        //Then
        Assertions.assertAll(
                () -> assertThat(foundInvites).isNotNull().isNotEmpty().hasSize(1),
                () -> AssertionsForClassTypes.assertThat(foundInvites.get(0).getId()).isEqualTo(invite.getId()),
                () -> AssertionsForClassTypes.assertThat(foundInvites.get(0).getPlayer1()).isEqualTo(invite.getPlayer1()),
                () -> AssertionsForClassTypes.assertThat(foundInvites.get(0).getPlayer2()).isEqualTo(invite.getPlayer2()),
                () -> AssertionsForClassTypes.assertThat(foundInvites.get(0).getStatus()).isEqualTo(invite.getStatus())
        );
    }

}