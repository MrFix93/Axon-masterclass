package nl.infosupport.demo.users.query.services;

import nl.infosupport.demo.users.query.readmodels.User;
import nl.infosupport.demo.users.query.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserViewServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserViewService userViewService;

    @Test
    void testFindAll() {
        //Given
        final User user = new User("Test@email.com", "Test", "Netherlands", "Hello I am test");
        when(userRepository.findAll()).thenReturn(List.of(user));

        //When
        userViewService.findAll();

        //Then
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateOrUpdate() {
        //Given
        final User user = new User("Test@email.com", "Test", "Netherlands", "Hello I am test");

        //When
        userViewService.createOrUpdate(user);

        //Then
        verify(userRepository, times(1)).save(user);
    }
}