package nl.infosupport.demo.game.command.controllers;

import nl.infosupport.demo.game.command.commandmodels.Move;
import nl.infosupport.demo.game.command.services.GameCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/games")
public class GameCommandController {

    private GameCommandService gameCommandService;

    @Autowired
    public GameCommandController(GameCommandService gameCommandService) {
        this.gameCommandService = gameCommandService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> makeMove(@PathVariable String id, @Valid @RequestBody Move move) throws ExecutionException, InterruptedException {

        gameCommandService.makeMove(id, move.getPlayerName(), move.getColor(), move.getPieceType(), move.getStartPosition().getFile(),
                move.getStartPosition().getRank(), move.getEndPosition().getFile(), move.getEndPosition().getRank(), move.isCapture())
        .get();

        return ResponseEntity.accepted().location(URI.create("/games/" + id)).build();
    }

}
