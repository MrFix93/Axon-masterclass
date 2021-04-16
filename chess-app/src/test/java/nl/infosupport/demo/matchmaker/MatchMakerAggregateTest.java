package nl.infosupport.demo.matchmaker;

import nl.infosupport.demo.matchmaker.command.commandmodels.Invite;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.commands.AcceptDeclineInviteCommand;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import nl.infosupport.demo.matchmaker.command.commands.SendInviteCommand;
import nl.infosupport.demo.matchmaker.command.exceptions.PolicyViolatedException;
import nl.infosupport.demo.matchmaker.events.InviteSentEvent;
import nl.infosupport.demo.matchmaker.events.InviteUpdatedEvent;
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

    @Test
    void testSendInvite() {
        //Given
        final String email = "test@email.com";
        final String email2 = "test2@email.com";
        final String name = "Test";
        final String name2 = "Test2";
        final User user1 = new User(email, name);
        final User user2 = new User(email2, name2);
        final UserAddedEvent userAddedEvent = new UserAddedEvent(MatchMakerAggregate.ID, user1);
        final UserAddedEvent userAddedEvent2 = new UserAddedEvent(MatchMakerAggregate.ID, user2);

        final SendInviteCommand sendInviteCommand = new SendInviteCommand(MatchMakerAggregate.ID, email, email2);

        final Invite expectedInvite = new Invite(email, email2, InviteStatus.PENDING);
        final InviteSentEvent inviteSentEvent = new InviteSentEvent(MatchMakerAggregate.ID, expectedInvite);

        fixture.given(userAddedEvent, userAddedEvent2)
                //When
                .when(sendInviteCommand)
                //Then
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        inviteSentEvent
                );
    }

    @Test
    void testSendInviteButEmail1DoesNotExist() {
        //Given
        final String email = "test@email.com";
        final String email2 = "test2@email.com";
        final String name = "Test";
        final String name2 = "Test2";
        final User user2 = new User(email2, name2);
        final UserAddedEvent userAddedEvent2 = new UserAddedEvent(MatchMakerAggregate.ID, user2);

        final SendInviteCommand sendInviteCommand = new SendInviteCommand(MatchMakerAggregate.ID, email, email2);

        fixture.given(userAddedEvent2)
                //When
                .when(sendInviteCommand)
                //Then
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("User: " + email + " does not exist.");
    }

    @Test
    void testSendInviteButEmail2DoesNotExist() {
        //Given
        final String email = "test@email.com";
        final String email2 = "test2@email.com";
        final String name = "Test";
        final String name2 = "Test2";
        final User user1 = new User(email, name);
        final UserAddedEvent userAddedEvent = new UserAddedEvent(MatchMakerAggregate.ID, user1);

        final SendInviteCommand sendInviteCommand = new SendInviteCommand(MatchMakerAggregate.ID, email, email2);

        fixture.given(userAddedEvent)
                //When
                .when(sendInviteCommand)
                //Then
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("User: " + email2 + " does not exist.");
    }

    @Test
    void testAcceptInvite() {
        //Given
        final String email = "test@email.com";
        final String email2 = "test2@email.com";
        final String name = "Test";
        final String name2 = "Test2";
        final User user1 = new User(email, name);
        final User user2 = new User(email2, name2);
        final UserAddedEvent userAddedEvent = new UserAddedEvent(MatchMakerAggregate.ID, user1);
        final UserAddedEvent userAddedEvent2 = new UserAddedEvent(MatchMakerAggregate.ID, user2);
        final Invite invite = new Invite(email, email2, InviteStatus.PENDING);
        final InviteSentEvent inviteSentEvent = new InviteSentEvent(MatchMakerAggregate.ID, invite);

        final AcceptDeclineInviteCommand acceptDeclineInviteCommand = new AcceptDeclineInviteCommand(MatchMakerAggregate.ID, email, email2, InviteStatus.ACCEPTED);

        final Invite expectedInvite = new Invite(email, email2, InviteStatus.ACCEPTED);
        final InviteUpdatedEvent inviteUpdatedEvent = new InviteUpdatedEvent(MatchMakerAggregate.ID, expectedInvite);

        fixture.given(userAddedEvent, userAddedEvent2, inviteSentEvent)
                //When
                .when(acceptDeclineInviteCommand)
                //Then
                .expectSuccessfulHandlerExecution()
                .expectEvents(inviteUpdatedEvent);
    }

    @Test
    void testDeclineInvite() {
        //Given
        final String email = "test@email.com";
        final String email2 = "test2@email.com";
        final String name = "Test";
        final String name2 = "Test2";
        final User user1 = new User(email, name);
        final User user2 = new User(email2, name2);
        final UserAddedEvent userAddedEvent = new UserAddedEvent(MatchMakerAggregate.ID, user1);
        final UserAddedEvent userAddedEvent2 = new UserAddedEvent(MatchMakerAggregate.ID, user2);
        final Invite invite = new Invite(email, email2, InviteStatus.PENDING);
        final InviteSentEvent inviteSentEvent = new InviteSentEvent(MatchMakerAggregate.ID, invite);

        final AcceptDeclineInviteCommand acceptDeclineInviteCommand = new AcceptDeclineInviteCommand(MatchMakerAggregate.ID, email, email2, InviteStatus.DECLINED);

        final Invite expectedInvite = new Invite(email, email2, InviteStatus.DECLINED);
        final InviteUpdatedEvent inviteUpdatedEvent = new InviteUpdatedEvent(MatchMakerAggregate.ID, expectedInvite);

        fixture.given(userAddedEvent, userAddedEvent2, inviteSentEvent)
                //When
                .when(acceptDeclineInviteCommand)
                //Then
                .expectSuccessfulHandlerExecution()
                .expectEvents(inviteUpdatedEvent);
    }

    @Test
    void testNoOpenInvite() {
        //Given
        final String email = "test@email.com";
        final String email2 = "test2@email.com";
        final String name = "Test";
        final String name2 = "Test2";
        final User user1 = new User(email, name);
        final User user2 = new User(email2, name2);
        final UserAddedEvent userAddedEvent = new UserAddedEvent(MatchMakerAggregate.ID, user1);
        final UserAddedEvent userAddedEvent2 = new UserAddedEvent(MatchMakerAggregate.ID, user2);
        final Invite invite = new Invite(email, email2, InviteStatus.PENDING);
        final InviteSentEvent inviteSentEvent = new InviteSentEvent(MatchMakerAggregate.ID, invite);

        final AcceptDeclineInviteCommand acceptDeclineInviteCommand = new AcceptDeclineInviteCommand(MatchMakerAggregate.ID, "gibberish1", email2, InviteStatus.DECLINED);

        fixture.given(userAddedEvent, userAddedEvent2, inviteSentEvent)
                //When
                .when(acceptDeclineInviteCommand)
                //Then
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("No pending invite found for player1: " + "gibberish1" + " and player2: " + email2);
    }
}