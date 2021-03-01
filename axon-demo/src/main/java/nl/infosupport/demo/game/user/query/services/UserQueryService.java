package nl.infosupport.demo.game.user.query.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.game.user.query.readmodels.User;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserQueryService {

    private final QueryGateway queryGateway;

    /**
     * Calls QueryGateway to find all WerkvoorraadItem
     *
     * @return a list of WerkvoorraadItem
     */
    public List<User> findAllUsers() throws ExecutionException, InterruptedException {
        return queryGateway.query("findAllUsers", null, ResponseTypes.multipleInstancesOf(User.class))
                .get();
    }
}
