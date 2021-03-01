package nl.infosupport.demo.game.events;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.users.User;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserRegisteredEvent extends Event {

    User user;

    public UserRegisteredEvent(String id, User user) {
        super(id);
        this.user = user;
    }

}
