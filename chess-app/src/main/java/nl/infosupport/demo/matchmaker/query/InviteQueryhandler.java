package nl.infosupport.demo.matchmaker.query;

import lombok.AllArgsConstructor;
import nl.infosupport.demo.matchmaker.query.readmodels.Invite;
import nl.infosupport.demo.matchmaker.query.services.InviteViewService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class InviteQueryhandler {

    private final InviteViewService inviteViewService;

    @QueryHandler(queryName = "findAllInvites")
    public List<Invite> findAll() {
        return inviteViewService.findAll();
    }
}
