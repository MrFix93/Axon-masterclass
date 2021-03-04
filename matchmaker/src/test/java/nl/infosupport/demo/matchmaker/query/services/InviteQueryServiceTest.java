package nl.infosupport.demo.matchmaker.query.services;

import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteQueryServiceTest {

    @Mock
    private QueryGateway queryGateway;

    @InjectMocks
    private InviteQueryService inviteQueryService;

    @Test
    void testFindAllInvites() throws ExecutionException, InterruptedException {
        //Arrange
        final Invite invite = new Invite("id", "Peter", "Raymond", "PENDING");

        when(queryGateway.query(anyString(), isNull(), any(ResponseType.class)))
                .thenReturn(CompletableFuture.completedFuture(Collections.singletonList(invite)));

        //Act
        final List<Invite> users = inviteQueryService.findAllUsers();

        //Assert
        assertThat(users).isNotNull().isNotEmpty().hasSize(1);
        verify(queryGateway, times(1)).query(anyString(), isNull(), any(ResponseType.class));
    }
}