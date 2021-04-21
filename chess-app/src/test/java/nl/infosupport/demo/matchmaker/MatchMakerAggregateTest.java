package nl.infosupport.demo.matchmaker;

import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import nl.infosupport.demo.matchmaker.events.UserAddedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatchMakerAggregateTest {

    private FixtureConfiguration<MatchMakerAggregate> fixture;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(MatchMakerAggregate.class);
    }

    @Test
    void testAddUser() {
        //Given
        final User user = new User("test@email.com", "Test");
        final AddUserCommand addUserCommand = new AddUserCommand(MatchMakerAggregate.ID, user);

        final UserAddedEvent userAddedEvent = new UserAddedEvent(MatchMakerAggregate.ID, user);

        fixture.givenNoPriorActivity()
                //When
                .when(addUserCommand)
                //Then
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        userAddedEvent
                );
    }
}