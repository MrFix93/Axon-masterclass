package nl.infosupport.demo.matchmaker.command.services;

import nl.infosupport.demo.matchmaker.MatchMakerAggregate;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
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

}