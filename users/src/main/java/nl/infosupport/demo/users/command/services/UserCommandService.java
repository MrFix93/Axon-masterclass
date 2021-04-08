package nl.infosupport.demo.users.command.services;


import lombok.AllArgsConstructor;
import nl.infosupport.demo.users.command.commands.RegisterUserCommand;
import nl.infosupport.demo.users.command.commandmodels.User;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> registerUser(User user) {
        final String id = UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString();
        final RegisterUserCommand registerUserCommand = new RegisterUserCommand(id, user);

        return commandGateway.send(registerUserCommand);
    }
}
