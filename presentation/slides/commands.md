# Commands & Aggregates

Note:

Zoals eerder besproken door Peter bestaan er dus Command en Query models.

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/cqrs.png)

Notes:
    Ook dit bestaat in Axon Framework. In een CQRS-gebaseerde applicatie is een belangrijke speler de Aggregate.

    Een Aggregate is een entiteit of een group van entiteiten wat altijd een constitente state heeft.
    Alle logica en state changes gaan door de aggregate heen, dit maakt de Aggregate "the prime building block"
    voor het implementeren van een Command Model in elk CQRS-gebaseerde applicatie.

    Ik heb zojuist vertelt dat alle state changes door een aggregate heen gaan, maar hoe gaan die dan
    door een Aggregate heen? Dat gebeurt doormiddel van "Commands".

    Een Command beschrijft de intentie (meestal een mutatie) van de actie en levert eventuele extra informatie aan
    om de actie uit te voeren.

    Bijvoorbeeld het aanmaken van iets.

    Deze twee concepten komen ook terug in het Axon Framework. Laten we kijken naar
    de event-storm en dan naar de User.

    Laten we beginnen met een Aggregate.

--

<pre><code class="java" data-trim data-line-numbers=" | 1 | 4">

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private String email;
    private String name;
    private String country;
    private String shortBio;

}
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape data-line-numbers=" | 6 | 7 | 8">

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException {
            //do something useful
        }
    }
    
</code></pre>

Notes:
CreationPolicy CREATE_IF_MISSING -> dit betekent dat de Aggregate de command moet afhandelen ook als deze niet bestaat. Hier kom ik later op terug wat dat dan precies betekent.
--

<pre><code class="java" data-trim data-noescape data-line-numbers=" | 3">

    @EqualsAndHashCode
    public class Command {
        @Getter(onMethod_ = {@TargetAggregateIdentifier}) 
        private String id;

        public Command() {

        }

        public Command(String id) {
            this.id = id;
        }
    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape>

    @EqualsAndHashCode(callSuper = true)
    @Value
    @AllArgsConstructor
    public class RegisterUserCommand extends Command {

        User user;

        public RegisterUserCommand(String id, User user) {
            super(id);
            this.user = user;
        }
    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape data-line-numbers=" | 5 | 7-11">
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> registerUser(User user) {
        final String id = UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString();
        final RegisterUserCommand registerUserCommand = new RegisterUserCommand(id, user);

        return commandGateway.send(registerUserCommand);
    }
}
</code></pre>

Notes: - Stuurt command op de CommandGateway - De Aggregate die een command handler hierop heeft zal dit verwerken

--

<pre><code class="java" data-trim data-noescape>

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException {
            if (id != null) {
                throw new PolicyViolatedException("Email already exists");
            }

        }
    }
    
</code></pre>

Notes: - Waarom check of id niet gelijk is aan null? Nou wanneer je create if missing gebruikt, dan zal die in de command handler komen wanneer de id nog niet bestaat.

    Want stel je zou geen create if missing gebruiken dan zal Axon je vertellen dat die de command NIET kan verwerken omdat er geen aggregate is die het meegegeven id heeft!

    Maar als dat zo is, dan kan je je waarschijnlijk wel afvragen, waarom moeten we hier dan nog een id != null check doen?

    Nou, met create if missing gaat die dus naar een nieuwe aggregate die een id nog niet heeft, OF hij gaat naar een bestaande aggregate die dat ID wel heeft.

    En als die al wel bestaat, dan wil je (afhankelijk van je use case natuurlijk) in dit geval niet nog een keer uitvoeren. Want uiteindelijk gaan we in deze command handler een user registreren.

--

<pre><code class="java" data-trim data-noescape>

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException {
            if (id != null) {
                throw new PolicyViolatedException("Email already exists");
            }

            final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(registerUserCommand.getId(), registerUserCommand.getUser());

            apply(userRegisteredEvent);
        }
    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape data-line-numbers=" | 6">

    @Getter
    @ToString
    @EqualsAndHashCode
    public abstract class Event {
        @Getter
        @TargetAggregateIdentifier
        private final String id;

        Event() {
            this.id = UUID.randomUUID().toString();
        }

        Event(String id) {
            this.id = id;
        }
    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape>

    @EqualsAndHashCode(callSuper = true)
    @Value
    public class UserRegisteredEvent extends Event {

        User user;

        public UserRegisteredEvent(String id, User user) {
            super(id);
            this.user = user;
        }

    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape>

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING)
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException {
            if (id != null) {
                throw new PolicyViolatedException("Email already exists");
            }

            final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(registerUserCommand.getId(), registerUserCommand.getUser());

            apply(userRegisteredEvent); 
        }

        @EventSourcingHandler
        void handle(UserRegisteredEvent userRegisteredEvent) {
            this.id = userRegisteredEvent.getId();
            this.email = userRegisteredEvent.getUser().getEmail();
            this.name = userRegisteredEvent.getUser().getName();
            this.country = userRegisteredEvent.getUser().getCountry();
            this.shortBio = userRegisteredEvent.getUser().getShortBio();
        }
    }
    
</code></pre>

Notes: - Het is dus heel belangrijk dat het zetten van de waardes van de aggregate echt pas in de EventSourcingHandler gebeurd en NIET in de command handler.

    Nou heel mooi dat het allemaal werkt, tenminste dat zeg ik nu en jullie vertrouwen me gewoon blind en misschien leg ik wel dingen uit die helemaal niet kloppen.

    En daarom moet je natuurlijk wel unit testen schrijven om de concluderen dat het ook echt goed werkt.

    Maar hoe schrijf je voor dit unit testen? Bedankt voor het vragen! Dat is namelijk een mooi bruggetje naar de volgende slide ;)

--

<pre><code class="java" data-trim data-noescape data-line-numbers=" | 4 | 8 | 15-21">

    @SpringBootTest
    public class UserAggregateTest {

    private AggregateTestFixture<UserAggregate> fixture;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(UserAggregate.class);
    }

    @Test
    public void testRegisterNewUser() {
        final User user = new User("Test", "test@mail.com", "Netherlands", "Hello");

        fixture.givenNoPriorActivity()
                .when(new RegisterUserCommand(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new UserRegisteredEvent(UUID.nameUUIDFromBytes("test@mail.com".getBytes(StandardCharsets.UTF_8)).toString(), user)
                        );
    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape>

    @Test
    public void testRegisterExistingUser() {
        fixture.given(new UserRegisteredEvent(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .when(new RegisterUserCommand(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("Email already exists");
    }
    
</code></pre>

--

<pre><code class="java" data-trim data-noescape>

    @RestController
    @RequestMapping("/users")
    public class UserCommandController {

        private UserCommandService userCommandService;

        @Autowired
        public UserCommandController(UserCommandService userCommandService) {
            this.userCommandService = userCommandService;
        }

        @PostMapping("")
        public ResponseEntity<Void> registerUser(@Valid @RequestBody User user) throws ExecutionException, InterruptedException {

            userCommandService.registerUser(user).get();

            return ResponseEntity.accepted().location(URI.create("/users")).build();
        }
    }
</code></pre>

--

Thoughts so far?

--

## MatchMaker assignment

<pre><code class="bash">

  git clone https://github.com/MrFix93/Axon-masterclass.git


</code></pre>

Note:

Kijk rond er is een User Aggregate en een MatchMaker Aggregate
Doe het zelf in Matchmaker
