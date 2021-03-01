package nl.infosupport.demo.game.services;

import nl.infosupport.demo.game.commands.RegisterUserCommand;
import nl.infosupport.demo.game.models.users.User;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    void testRegisterUser() {
        //Given
        final User user = new User("Raymond", "test@email.com", "Netherlands", "Hello");
        final RegisterUserCommand registerUserCommand = new RegisterUserCommand(user.getEmail(), user);
        //When
        userCommandService.registerUser(user);

        //Then
        verify(commandGateway, times(1)).send(registerUserCommand);
    }
}