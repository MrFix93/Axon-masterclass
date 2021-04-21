package nl.infosupport.demo.users;

import nl.infosupport.demo.users.command.commandmodels.User;
import nl.infosupport.demo.users.command.commands.RegisterUserCommand;
import nl.infosupport.demo.users.command.exceptions.PolicyViolatedException;
import nl.infosupport.demo.users.events.UserRegisteredEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@SpringBootTest
public class UserAggregateTest {

    private AggregateTestFixture<UserAggregate> fixture;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(UserAggregate.class);
    }

    @Test
    public void testRegisterNewUser() {
        final User user = new User("Test", "test@mail.com", "Netherlands", "Hello");

        fixture.givenNoPriorActivity()
                .when(new RegisterUserCommand(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new UserRegisteredEvent(UUID.nameUUIDFromBytes("test@mail.com".getBytes(StandardCharsets.UTF_8)).toString(), user)
                        );
    }

    @Test
    public void testRegisterNewUserButAlreadyExists() {
        final User user = new User("Test", "test@mail.com", "Netherlands", "Hello");

        fixture.given(new UserRegisteredEvent(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .when(new RegisterUserCommand(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("Email already exists");
    }
}
