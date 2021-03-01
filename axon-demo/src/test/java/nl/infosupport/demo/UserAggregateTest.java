package nl.infosupport.demo;

import nl.infosupport.demo.game.UserAggregate;
import nl.infosupport.demo.game.commands.RegisterUserCommand;
import nl.infosupport.demo.game.events.UserRegisteredEvent;
import nl.infosupport.demo.game.exceptions.PolicyViolatedException;
import nl.infosupport.demo.game.models.users.User;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAggregateTest {

    private AggregateTestFixture<UserAggregate> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(UserAggregate.class);
    }

    @Test
    public void testRegisterNewUser() {
        final User user = new User("Raymond", "test@mail.com");

        fixture.givenNoPriorActivity()
                .when(new RegisterUserCommand("test@mail.com", user))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new UserRegisteredEvent("test@mail.com", user)
                        );
    }

    @Test
    public void testRegisterNewUserButAlreadyExists() {
        final User user = new User("Raymond", "test@mail.com");

        fixture.given(new UserRegisteredEvent("test@mail.com", user))
                .when(new RegisterUserCommand("test@mail.com", user))
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("Email already exists");
    }
}
