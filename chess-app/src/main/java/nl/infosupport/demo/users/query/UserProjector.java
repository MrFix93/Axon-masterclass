package nl.infosupport.demo.users.query;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.users.events.UserRegisteredEvent;
import nl.infosupport.demo.users.query.readmodels.User;
import nl.infosupport.demo.users.query.services.UserViewService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserProjector {

    private final UserViewService userViewService;

    @EventHandler
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        final User user = new User(userRegisteredEvent.getId(), userRegisteredEvent.getUser().getEmail(), userRegisteredEvent.getUser().getName(), userRegisteredEvent.getUser().getCountry(), userRegisteredEvent.getUser().getShortBio());

        userViewService.createOrUpdate(user);
    }
}
