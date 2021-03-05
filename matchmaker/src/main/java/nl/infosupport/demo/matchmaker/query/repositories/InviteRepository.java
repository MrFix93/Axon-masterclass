package nl.infosupport.demo.matchmaker.query.repositories;

import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends CrudRepository<Invite, String> {

    List<Invite> findAll();
    List<Invite> findByPlayer1AndPlayer2AndStatus(String player1, String player2, String status);
}

