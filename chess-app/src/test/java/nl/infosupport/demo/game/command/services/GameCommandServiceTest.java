package nl.infosupport.demo.game.command.services;

import nl.infosupport.demo.game.command.commands.StartGameCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameCommandServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @InjectMocks
    private GameCommandService gameCommandService;

    @Test
    void testStartGameCommand() {
        //Given
        final String player1 = "player1";
        final String player2 = "player2";

        //When
        gameCommandService.startGame(player1, player2);

        //Then
        verify(commandGateway, times(1)).send(any(StartGameCommand.class));
    }
}