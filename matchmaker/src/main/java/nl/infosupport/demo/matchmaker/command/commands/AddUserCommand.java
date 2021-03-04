package nl.infosupport.demo.matchmaker.command.commands;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddUserCommand extends Command {

    User user;

    public AddUserCommand(String id, User user) {
        super(id);
        this.user = user;
    }
}