package nl.infosupport.demo.game.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.game.models.users.User;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class RegisterUserCommand extends Command {

    User user;

    public RegisterUserCommand(String id, User user) {
        super(id);
        this.user = user;
    }
}
