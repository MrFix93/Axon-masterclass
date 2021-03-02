package nl.infosupport.demo.users.command.controllers;

import nl.infosupport.demo.users.command.services.UserCommandService;
import nl.infosupport.demo.users.command.commandmodels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserCommandController {

    private UserCommandService userCommandService;

    @Autowired
    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody User user) {

        userCommandService.registerUser(user);

        return ResponseEntity.accepted().location(URI.create("/users")).build();
    }
}
