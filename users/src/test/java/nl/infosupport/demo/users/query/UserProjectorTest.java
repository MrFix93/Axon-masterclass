package nl.infosupport.demo.users.query;

import nl.infosupport.demo.users.events.UserRegisteredEvent;
import nl.infosupport.demo.users.command.commandmodels.User;
import nl.infosupport.demo.users.query.services.UserViewService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@SpringBootTest
class UserProjectorTest {

    @MockBean
    private UserViewService userViewService;
    @Autowired
    private UserProjector userProjector;

    @Test
    void testUserRegisteredEvent() {
        //Given
        final User user = new User("Test", "test@email.com", "Netherlands", "Hello I am Test");
        final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent("test@email.com", user);

        final ArgumentCaptor<nl.infosupport.demo.users.query.readmodels.User> captor = ArgumentCaptor.forClass(nl.infosupport.demo.users.query.readmodels.User.class);

        //When
        userProjector.handle(userRegisteredEvent);

        //Then
        Mockito.verify(userViewService, times(1)).createOrUpdate(captor.capture());
        final nl.infosupport.demo.users.query.readmodels.User savedUser = captor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo(userRegisteredEvent.getUser().getEmail());
        assertThat(savedUser.getName()).isEqualTo(userRegisteredEvent.getUser().getName());
        assertThat(savedUser.getCountry()).isEqualTo(userRegisteredEvent.getUser().getCountry());
        assertThat(savedUser.getBiography()).isEqualTo(userRegisteredEvent.getUser().getShortBio());
    }
}
