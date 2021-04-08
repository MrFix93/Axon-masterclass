package nl.infosupport.demo.users.query;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.users.query.readmodels.User;
import nl.infosupport.demo.users.query.services.UserViewService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserQueryHandler {

    private final UserViewService userViewService;

    @QueryHandler(queryName = "findAllUsers")
    public List<User> findAll() {
        return userViewService.findAll();
    }
}
