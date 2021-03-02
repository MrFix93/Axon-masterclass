package nl.infosupport.demo.users.command.services;

import nl.infosupport.demo.users.command.commands.RegisterUserCommand;
import nl.infosupport.demo.users.command.commandmodels.User;
import nl.infosupport.demo.users.command.services.UserCommandService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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