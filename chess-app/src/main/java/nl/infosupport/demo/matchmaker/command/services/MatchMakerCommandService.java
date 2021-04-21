package nl.infosupport.demo.matchmaker.command.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.MatchMakerAggregate;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class MatchMakerCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> addUser(User user) {
        final AddUserCommand registerUserCommand = new AddUserCommand(MatchMakerAggregate.ID, user);

        return commandGateway.send(registerUserCommand);
    }
}
