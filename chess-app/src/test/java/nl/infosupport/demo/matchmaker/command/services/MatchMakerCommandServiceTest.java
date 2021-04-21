package nl.infosupport.demo.matchmaker.command.services;

import nl.infosupport.demo.matchmaker.MatchMakerAggregate;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.commands.AcceptDeclineInviteCommand;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import nl.infosupport.demo.matchmaker.command.commands.SendInviteCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MatchMakerCommandServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private MatchMakerCommandService matchMakerCommandService;

    @Test
    void testAddUserCommand() {
        //Given
        final User user = new User("test@email.com", "Raymond" );
        final AddUserCommand addUserCommand = new AddUserCommand(MatchMakerAggregate.ID, user);

        //When
        matchMakerCommandService.addUser(user);

        //Then
        verify(commandGateway, times(1)).send(addUserCommand);
    }

    @Test
    void testInviteCommand() {
        //Given
        final String player1 = "player1@email.com";
        final String player2 = "player2@email.com";
        final SendInviteCommand sendInviteCommand = new SendInviteCommand(MatchMakerAggregate.ID, player1, player2);

        //When
        matchMakerCommandService.invite(player1, player2);

        //Then
        verify(commandGateway, times(1)).send(sendInviteCommand);
    }

    @Test
    void testAcceptDeclineCommand() {
        //Given
        final String player1 = "player1@email.com";
        final String player2 = "player2@email.com";
        final AcceptDeclineInviteCommand acceptDeclineInviteCommand = new AcceptDeclineInviteCommand(MatchMakerAggregate.ID, player1, player2, InviteStatus.ACCEPTED);

        //When
        matchMakerCommandService.acceptDecline(player1, player2, InviteStatus.ACCEPTED);

        //Then
        verify(commandGateway, times(1)).send(acceptDeclineInviteCommand);
    }
}