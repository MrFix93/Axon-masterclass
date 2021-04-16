package nl.infosupport.demo.users.query;

import nl.infosupport.demo.users.query.readmodels.User;
import nl.infosupport.demo.users.query.services.UserViewService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserQueryHandlerTest {

    @Autowired
    private UserQueryHandler userQueryHandler;
    @MockBean
    private UserViewService userViewService;

    @Test
    void testFindAllUsers() {
        //Given
        final User user = new User("Test@email.com", "Test", "Netherlands", "Hello I am test");
        when(userViewService.findAll()).thenReturn(List.of(user));

        //When
        final List<User> foundUsers = userQueryHandler.findAll();

        //Then
        Assertions.assertAll(
                () -> assertThat(foundUsers).isNotNull().isNotEmpty().hasSize(1),
                () -> AssertionsForClassTypes.assertThat(foundUsers.get(0).getEmail()).isEqualTo(user.getEmail()),
                () -> AssertionsForClassTypes.assertThat(foundUsers.get(0).getName()).isEqualTo(user.getName()),
                () -> AssertionsForClassTypes.assertThat(foundUsers.get(0).getCountry()).isEqualTo(user.getCountry()),
                () -> AssertionsForClassTypes.assertThat(foundUsers.get(0).getBiography()).isEqualTo(user.getBiography())
        );
    }
}