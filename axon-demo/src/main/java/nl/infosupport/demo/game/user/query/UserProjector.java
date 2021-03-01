package nl.infosupport.demo.game.user.query;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.game.events.UserRegisteredEvent;
import nl.infosupport.demo.game.user.query.readmodels.User;
import nl.infosupport.demo.game.user.query.services.UserViewService;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
@ProcessingGroup("storedEvents") //TODO: processinggroup zetten
public class UserProjector {

    private final UserViewService userViewService;

    @EventHandler
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        final User user = new User(userRegisteredEvent.getId(), userRegisteredEvent.getUser().getName(), userRegisteredEvent.getUser().getCountry(), userRegisteredEvent.getUser().getShortBio());

        userViewService.createOrUpdate(user);
    }
}
