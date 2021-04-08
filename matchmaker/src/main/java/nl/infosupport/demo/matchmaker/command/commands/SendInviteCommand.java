package nl.infosupport.demo.matchmaker.command.commands;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class SendInviteCommand extends Command {

    String player1;
    String player2;

    public SendInviteCommand(String id, String player1, String player2) {
        super(id);
        this.player1 = player1;
        this.player2 = player2;
    }
}
