package nl.infosupport.demo.game.command.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor
public class StartGameCommand extends Command {
    String whitePlayer;
    String blackPlayer;

    public StartGameCommand(String id, String whitePlayer, String blackPlayer) {
        super(id);
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }
}

