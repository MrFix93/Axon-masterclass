package nl.infosupport.demo.game.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.game.commands.StartGameCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class GameCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> startGame(String player1, String player2) {
        final String id = UUID.randomUUID().toString();
        final StartGameCommand startGameCommand = new StartGameCommand(id, player1, player2);

        return commandGateway.send(startGameCommand);
    }
}