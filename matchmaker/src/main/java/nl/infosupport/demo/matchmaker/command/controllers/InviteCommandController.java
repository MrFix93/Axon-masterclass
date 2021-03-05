package nl.infosupport.demo.matchmaker.command.controllers;

import nl.infosupport.demo.matchmaker.command.controllers.models.Invite;
import nl.infosupport.demo.matchmaker.command.services.MatchMakerCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/invites")
public class InviteCommandController {

    private final MatchMakerCommandService matchMakerCommandService;

    @Autowired
    public InviteCommandController(MatchMakerCommandService matchMakerCommandService) {
        this.matchMakerCommandService = matchMakerCommandService;
    }

    @PostMapping("")
    public ResponseEntity<Void> invite(@RequestBody Invite invite) throws ExecutionException, InterruptedException {
        matchMakerCommandService.invite(invite.getPlayer1(), invite.getPlayer2());

        return ResponseEntity.accepted().location(URI.create("/invites")).build();
    }

    @PatchMapping("")
    public ResponseEntity<Void> updateInvite(@RequestBody nl.infosupport.demo.matchmaker.command.commandmodels.Invite invite) {
        matchMakerCommandService.acceptDecline(invite.getPlayer1(), invite.getPlayer2(), invite.getStatus());

        return ResponseEntity.accepted().location(URI.create("/invites")).build();
    }
}
