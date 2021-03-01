package nl.infosupport.demo.game.user.query.controllers;

import nl.infosupport.demo.game.user.query.readmodels.User;
import nl.infosupport.demo.game.user.query.services.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @Autowired
    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("")
    public List<User> getWerkvoorraad() throws ExecutionException, InterruptedException {
        return userQueryService.findAllUsers();
    }
}
