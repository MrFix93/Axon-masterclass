package nl.infosupport.demo.matchmaker.events;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.matchmaker.command.commandmodels.Invite;

@EqualsAndHashCode(callSuper = true)
@Value
public class InviteUpdatedEvent extends Event {

    Invite invite;

    public InviteUpdatedEvent(String id, Invite invite) {
        super(id);
        this.invite = invite;
    }
}
