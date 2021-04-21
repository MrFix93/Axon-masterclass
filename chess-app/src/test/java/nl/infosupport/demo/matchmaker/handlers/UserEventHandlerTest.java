package nl.infosupport.demo.matchmaker.handlers;

import nl.infosupport.demo.matchmaker.command.services.MatchMakerCommandService;
import nl.infosupport.demo.users.command.commandmodels.User;
import nl.infosupport.demo.users.events.UserRegisteredEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventHandlerTest {

    @Mock
    private MatchMakerCommandService matchMakerCommandService;

    @InjectMocks
    private UserEventHandler userEventHandler;

    @Test
    void testHandleUserRegisteredEvent() {
        //Given
        final User user = new User("testname", "email@email.com", "Netherlands", "short bio");
        final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent("email@email.com", user);

        //When
        userEventHandler.handle(userRegisteredEvent);

        //Then
        verify(matchMakerCommandService, times(1)).addUser(any(nl.infosupport.demo.matchmaker.command.commandmodels.User.class));
    }
}