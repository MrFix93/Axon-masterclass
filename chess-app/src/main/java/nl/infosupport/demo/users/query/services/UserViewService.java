package nl.infosupport.demo.users.query.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.users.query.readmodels.User;
import nl.infosupport.demo.users.query.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserViewService {

    private final UserRepository userRepository;

    /**
     * Finds all {@link User}
     *
     * @return the list of all User
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Creates or updates an instance of User
     *
     * @param user the User to be saved
     */
    public void createOrUpdate(User user) {
        userRepository.save(user);
    }
}
