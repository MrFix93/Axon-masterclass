package nl.infosupport.demo.users.query.repositories;

import nl.infosupport.demo.users.query.readmodels.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}

