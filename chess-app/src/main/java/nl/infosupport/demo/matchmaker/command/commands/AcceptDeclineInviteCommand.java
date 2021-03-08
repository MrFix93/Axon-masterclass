package nl.infosupport.demo.matchmaker.command.commands;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;

@EqualsAndHashCode(callSuper = true)
@Value
public class AcceptDeclineInviteCommand extends Command {

    String player1;
    String player2;
    InviteStatus inviteStatus;

    public AcceptDeclineInviteCommand(String id, String player1, String player2, InviteStatus inviteStatus) {
        super(id);
        this.player1 = player1;
        this.player2 = player2;
        this.inviteStatus = inviteStatus;
    }
}
