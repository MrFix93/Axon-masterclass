# Queries

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/cqrs.png)

--

## Let's recall

Commands changes state..<br>
...Queries return the state

--

- Both can have different data models
- Flexibility in data representation

--

Let's see the domain model..

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/domainmodel.jpg)

--

<!-- .slide: data-background-color="white" -->

<pre>
    <code class="language-plantuml">
    @startuml

    [Projector] as projector
    database Datastore as datastore
    [EventGateway] as eventgateway
    [QueryGateway] as querygateway
    [QueryHandler] as queryhandler
    [RestController] as rest

    rest -> querygateway : request query
    queryhandler -down-> querygateway : listens to
    queryhandler -> datastore : queries
    projector -right-> eventgateway : listens to events
    projector -down-> datastore : persist into
    
    cloud MessageBroker
    
    eventgateway -> MessageBroker

    @enduml
</code>
</pre>

--

## Projector

Creates a view from the handled events

<pre>
<code class="java" data-trim data-line-numbers="1,4">
    @EventHandler
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        final User user = new User();
        userViewService.createOrUpdate(user);
    }
</code>
</pre>

--

## Query Handler

Handles the queries as published on the query gateway

<pre>
<code class="java" data-trim data-line-numbers="3">
    private final UserViewService userViewService;

    @QueryHandler(queryName = "findAllUsers")
    public List<User> findAll() {
        return userViewService.findAll();
    }
}
</code>
</pre>

--

## Query Service/Rest Controller

Sends a query over the Query Gateway

<pre>
<code class="java" data-trim data-line-numbers="3,11">
public class UserQueryService {

    private final QueryGateway queryGateway;

    /**
     * Calls QueryGateway to find all Users
     *
     * @return a list of Users
     */
    public List<User> findAllUsers() throws ExecutionException, InterruptedException {
        return queryGateway.query("findAllUsers", null, ResponseTypes.multipleInstancesOf(User.class))
                .get();
    }
}
</code>
</pre>

--

<!-- .slide: data-background-color="white" -->

<pre>
    <code class="language-plantuml">
    @startuml

    [Projector] as projector
    database Datastore as datastore
    [EventGateway] as eventgateway
    [QueryGateway] as querygateway
    [QueryHandler] as queryhandler
    [RestController] as rest

    rest -> querygateway : request query
    queryhandler -down-> querygateway : listens to
    queryhandler -> datastore : queries
    projector -right-> eventgateway : listens to events
    projector -down-> datastore : persist into
    
    cloud MessageBroker
    
    eventgateway -> MessageBroker

    @enduml
</code>
</pre>

--

## Challenge:

Given a set of events from the event storm.
Write a matchmaker projector that returns the status of all invite's.
