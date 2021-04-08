package nl.infosupport.demo.matchmaker.command.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.infosupport.demo.matchmaker.command.commandmodels.InviteStatus;
import nl.infosupport.demo.matchmaker.command.controllers.models.Invite;
import nl.infosupport.demo.matchmaker.command.services.MatchMakerCommandService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InviteCommandControllerTest {

    @MockBean
    private MatchMakerCommandService matchMakerCommandService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPostInvites202() throws Exception {
        // Given
        when(matchMakerCommandService.invite(anyString(), anyString())).thenReturn(CompletableFuture.completedFuture("1234"));
        final Invite invite = new Invite("Raymond", "Peter");

        // When & Then
        mockMvc. perform(MockMvcRequestBuilders
                .post("/invites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invite)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""))
                .andExpect(header().string("location", "/invites"));

        verify(matchMakerCommandService, times(1)).invite(anyString(), anyString());
    }

    @Test
    void testPatchInvites202() throws Exception {
        // Given
        when(matchMakerCommandService.acceptDecline(anyString(), anyString(), any(InviteStatus.class))).thenReturn(CompletableFuture.completedFuture("1234"));
        final nl.infosupport.demo.matchmaker.command.commandmodels.Invite invite = new nl.infosupport.demo.matchmaker.command.commandmodels.Invite("Raymond", "Peter", InviteStatus.ACCEPTED);

        // When & Then
        mockMvc. perform(MockMvcRequestBuilders
                .patch("/invites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invite)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""))
                .andExpect(header().string("location", "/invites"));

        verify(matchMakerCommandService, times(1)).acceptDecline(anyString(), anyString(), any(InviteStatus.class));
    }

    private <T> String asJsonString(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(object);
    }
}