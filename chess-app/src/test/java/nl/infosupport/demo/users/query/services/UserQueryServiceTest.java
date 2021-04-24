package nl.infosupport.demo.users.query.services;

import nl.infosupport.demo.users.query.readmodels.User;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {
    @Mock
    private QueryGateway queryGateway;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    void testFindAllUsers() throws ExecutionException, InterruptedException {
        //Arrange
        final User user = new User("id", "Test@email.com", "Test", "Netherlands", "Hello I am test");

        when(queryGateway.query(anyString(), isNull(), any(ResponseType.class)))
                .thenReturn(CompletableFuture.completedFuture(Collections.singletonList(user)));

        //Act
        final List<User> users = userQueryService.findAllUsers();

        //Assert
        assertThat(users).isNotNull().isNotEmpty().hasSize(1);
        verify(queryGateway, times(1)).query(anyString(), isNull(), any(ResponseType.class));
    }
}
