package nl.infosupport.demo.game.services;


import lombok.AllArgsConstructor;
import nl.infosupport.demo.game.commands.RegisterUserCommand;
import nl.infosupport.demo.game.models.users.User;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> registerUser(User user) {
        final RegisterUserCommand registerUserCommand = new RegisterUserCommand(user.getEmail(), user);

        return commandGateway.send(registerUserCommand);
    }
}
