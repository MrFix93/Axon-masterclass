package nl.infosupport.demo.game.user.query.repositories;

import nl.infosupport.demo.game.user.query.readmodels.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}

