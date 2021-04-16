package nl.infosupport.demo.matchmaker.query.controllers;

import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.services.InviteQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InviteQueryControllerTest {

    @MockBean
    private InviteQueryService inviteQueryService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetInvites200() throws Exception {
        // Given
        final Invite invite = new Invite("Raymond", "Peter", "PENDING");
        when(inviteQueryService.findAllInvites()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/invites"))
                .andExpect(status().isOk());

        verify(inviteQueryService, times(1)).findAllInvites();
    }
}