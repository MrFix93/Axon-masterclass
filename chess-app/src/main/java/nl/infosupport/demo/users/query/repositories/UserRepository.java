package nl.infosupport.demo.users.query.repositories;

import nl.infosupport.demo.users.query.readmodels.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();
}

