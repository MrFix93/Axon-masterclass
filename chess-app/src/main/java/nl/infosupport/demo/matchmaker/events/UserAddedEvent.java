package nl.infosupport.demo.matchmaker.events;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserAddedEvent extends Event {

    User user;

    public UserAddedEvent(String id, User user) {
        super(id);
        this.user = user;
    }
}
