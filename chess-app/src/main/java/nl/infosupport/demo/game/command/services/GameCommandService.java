package nl.infosupport.demo.game.command.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.game.command.commands.MakeMoveCommand;
import nl.infosupport.demo.game.command.commands.StartGameCommand;
import nl.infosupport.demo.game.command.models.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class GameCommandService {

    private final CommandGateway commandGateway;
    private final PiecesFactory piecesFactory;

    public CompletableFuture<String> startGame(String player1, String player2) {
        final String id = UUID.randomUUID().toString();
        final StartGameCommand startGameCommand = new StartGameCommand(id, player1, player2);

        return commandGateway.send(startGameCommand);
    }

    public CompletableFuture<String> makeMove(String id, String playerName, String color, String pieceType, String startFile,
                                              int startRank, String endFile, int endRank, boolean isCapture) {

        final ChessColor chessColor = ChessColor.valueOf(color);
        final Piece piece = piecesFactory.getPiece(pieceType, chessColor);
        final Square startPosition = Square.of(File.valueOf(startFile), new Rank(startRank));
        final Square endPosition = Square.of(File.valueOf(endFile), new Rank(endRank));
        final Player player = new Player(playerName, chessColor);

        final MakeMoveCommand makeMoveCommand = new MakeMoveCommand(id, piece, startPosition, endPosition, isCapture, player);

        return commandGateway.send(makeMoveCommand);
    }
}
