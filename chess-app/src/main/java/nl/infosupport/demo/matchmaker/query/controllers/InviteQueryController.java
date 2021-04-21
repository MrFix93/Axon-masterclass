package nl.infosupport.demo.matchmaker.query.controllers;

import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.services.InviteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/invites")
public class InviteQueryController {

    private final InviteQueryService inviteQueryService;

    @Autowired
    public InviteQueryController(InviteQueryService inviteQueryService) {
        this.inviteQueryService = inviteQueryService;
    }

    @GetMapping("")
    public List<Invite> getInvites() throws ExecutionException, InterruptedException {
        return inviteQueryService.findAllInvites();
    }
}
