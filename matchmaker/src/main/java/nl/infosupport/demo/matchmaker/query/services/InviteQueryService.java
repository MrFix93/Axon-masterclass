package nl.infosupport.demo.matchmaker.query.services;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class InviteQueryService {

    private final QueryGateway queryGateway;

    /**
     * Calls QueryGateway to find all Invites
     *
     * @return a list of Invites
     */
    public List<Invite> findAllUsers() throws ExecutionException, InterruptedException {
        return queryGateway.query("findAllInvites", null, ResponseTypes.multipleInstancesOf(Invite.class))
                .get();
    }
}
