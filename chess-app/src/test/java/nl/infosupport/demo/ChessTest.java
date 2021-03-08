package nl.infosupport.demo;

import nl.infosupport.demo.matchmaker.command.services.MatchMakerCommandService;
import nl.infosupport.demo.users.command.commandmodels.User;
import nl.infosupport.demo.users.command.services.UserCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class ChessTest {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private MatchMakerCommandService matchMakerCommandService;

    @Test
    void test() throws ExecutionException, InterruptedException {
        final User user = new User("Raymond", "raymond123@email.nl", "Netherlands", "test");
//        final User user2 = new User("Peter", "peter1@email.nl", "Netherlands", "test");

        userCommandService.registerUser(user).get();
//        userCommandService.registerUser(user2).get();

//        final Invite invite = new Invite("raymond@email.nl", "peter@email.nl", InviteStatus.PENDING);
//        matchMakerCommandService.invite("raymond@email.nl", "peter@email.nl");

        //User user = new User("test", "test");

        //matchMakerCommandService.addUser(user);
        System.out.println("test");
    }
}
