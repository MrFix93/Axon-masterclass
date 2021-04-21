package nl.infosupport.demo.matchmaker.handlers;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.services.MatchMakerCommandService;
import nl.infosupport.demo.users.events.UserRegisteredEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserEventHandler {

    private MatchMakerCommandService matchMakerCommandService;

    @EventHandler
    public void handle(UserRegisteredEvent event) {
        final User user = new User(event.getUser().getEmail(), event.getUser().getName());
        matchMakerCommandService.addUser(user);
    }

}
