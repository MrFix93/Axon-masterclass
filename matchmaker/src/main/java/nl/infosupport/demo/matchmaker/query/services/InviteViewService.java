package nl.infosupport.demo.matchmaker.query.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.repositories.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class InviteViewService {

    private final InviteRepository inviteRepository;

    /**
     * Finds all {@link Invite}
     *
     * @return the list of all Invites
     */
    public List<Invite> findAll() {
        return inviteRepository.findAll();
    }

    /**
     * Find single {@link Invite} matching player1 and player2 with status
     * @param player1 player1
     * @param player2 player2
     * @param status Invite status
     * @return The Invite matching player1 and player2 with status
     */
    public Invite findByPlayer1AndPlayer2AndStatus(String player1, String player2, String status) {
        final List<Invite> invitesByPlayer1AndPlayer2AndStatus = inviteRepository.findByPlayer1AndPlayer2AndStatus(player1, player2, status);

        return invitesByPlayer1AndPlayer2AndStatus.get(0);
    }

    /**
     * Creates or updates an instance of Invite
     *
     * @param invite the Invite to be saved
     */
    public void createOrUpdate(Invite invite) {
        inviteRepository.save(invite);
    }
}
