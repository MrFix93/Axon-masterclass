package nl.infosupport.demo.game.events;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.users.User;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserRegisteredEvent extends Event {

    User user;

    public UserRegisteredEvent(UUID uuid, User user) {
        super(uuid);
        this.user = user;
    }

}
