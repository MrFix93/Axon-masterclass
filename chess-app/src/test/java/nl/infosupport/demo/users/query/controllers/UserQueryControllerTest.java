package nl.infosupport.demo.users.query.controllers;

import nl.infosupport.demo.users.query.readmodels.User;
import nl.infosupport.demo.users.query.services.UserQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserQueryControllerTest {

    @MockBean
    private UserQueryService userQueryService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUsers200() throws Exception {
        // Given
        final User user = new User("id", "Test@email.com", "Test", "Netherlands", "Hello I am test");
        when(userQueryService.findAllUsers()).thenReturn(List.of(user));

        // When & Then
        mockMvc. perform(MockMvcRequestBuilders
                .get("/users/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userQueryService, times(1)).findAllUsers();
    }
}