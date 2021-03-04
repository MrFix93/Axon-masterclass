package nl.infosupport.demo.matchmaker.command.commandmodels;

import lombok.Value;

@Value
public class Invite {

    String player1;
    String player2;
    InviteStatus status;

    public Invite withStatus(InviteStatus status) {
        return new Invite(this.player1, this.player2, status);
    }
}
