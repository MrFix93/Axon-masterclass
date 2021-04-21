package nl.infosupport.demo.matchmaker.command.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.MatchMakerAggregate;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.command.commandmodels.User;
import nl.infosupport.demo.matchmaker.command.commands.AcceptDeclineInviteCommand;
import nl.infosupport.demo.matchmaker.command.commands.AddUserCommand;
import nl.infosupport.demo.matchmaker.command.commands.SendInviteCommand;
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

    public CompletableFuture<String> invite(String player1, String player2) {
        final SendInviteCommand sendInviteCommand = new SendInviteCommand(MatchMakerAggregate.ID, player1, player2);

        return commandGateway.send(sendInviteCommand);
    }

    public CompletableFuture<String> acceptDecline(String player1, String player2, InviteStatus inviteStatus) {
        final AcceptDeclineInviteCommand acceptDeclineInviteCommand = new AcceptDeclineInviteCommand(MatchMakerAggregate.ID, player1, player2, inviteStatus);

        return commandGateway.send(acceptDeclineInviteCommand);
    }
}
