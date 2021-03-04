package nl.infosupport.demo.users;

import nl.infosupport.demo.users.command.commands.RegisterUserCommand;
import nl.infosupport.demo.users.events.UserRegisteredEvent;
import nl.infosupport.demo.users.command.exceptions.PolicyViolatedException;
import nl.infosupport.demo.users.command.commandmodels.User;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
                .when(new RegisterUserCommand("test@mail.com", user))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new UserRegisteredEvent("test@mail.com", user)
                        );
    }

    @Test
    public void testRegisterNewUserButAlreadyExists() {
        final User user = new User("Test", "test@mail.com", "Netherlands", "Hello");

        fixture.given(new UserRegisteredEvent("test@mail.com", user))
                .when(new RegisterUserCommand("test@mail.com", user))
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("Email already exists");
    }
}
