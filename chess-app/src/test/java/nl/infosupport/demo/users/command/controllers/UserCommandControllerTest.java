package nl.infosupport.demo.users.command.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.infosupport.demo.users.command.commandmodels.User;
import nl.infosupport.demo.users.command.services.UserCommandService;
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

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserCommandControllerTest {

    @MockBean
    private UserCommandService userCommandService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPostUsers202() throws Exception {
        // Given
        when(userCommandService.registerUser(any(User.class))).thenReturn(CompletableFuture.completedFuture("1234"));
        final User user = new User("Raymond", "test@email.com", "Netherlands", "Hello");

        // When & Then
        mockMvc. perform(MockMvcRequestBuilders
                .post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""))
                .andExpect(header().string("location", "/users"));

        verify(userCommandService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testPostUsers400() throws Exception {
        // Given
        when(userCommandService.registerUser(any(User.class))).thenReturn(CompletableFuture.completedFuture("1234"));
        final User user = new User(null, "test@email.com", "Netherlands", "Hello");

        // When & Then
        mockMvc. perform(MockMvcRequestBuilders
                .post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));

        verify(userCommandService, times(0)).registerUser(any(User.class));
    }

    private <T> String asJsonString(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(object);
    }
}