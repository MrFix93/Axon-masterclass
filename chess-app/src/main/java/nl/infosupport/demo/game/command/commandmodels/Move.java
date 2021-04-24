package nl.infosupport.demo.game.command.commandmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Move {

    private String playerName;
    private String color;
    private String pieceType;
    private Square startPosition;
    private Square endPosition;
    private boolean isCapture;

}
