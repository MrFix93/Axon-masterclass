# Commands & Aggregates

In een CQRS-gebaseerde applicatie een belangrijke speler de Aggregate.

Een Aggregate is een entiteit of een group van entiteiten wat altijd een constitente state heeft. 
Alle logica en state changes gaan door de aggregate heen, dit maakt de Aggregate "the prime building block" 
voor het implementeren van een Command Model in elk CQRS-gebaseerde applicatie.

Hier boven heb ik vertelt dat alle state changes door een aggregate heen gaan, maar hoe gaan die dan 
door een Aggregate heen? Dat gebeurt doormiddel van "Commands".

Een Command beschrijft de intentie (meestal een mutatie) van de actie en levert eventuele extra informatie aan
om de actie uit te voeren. 

Bijvoorbeeld en aanmaken van iets.


Deze twee concepten komen ook terug in het Axon Framework. Laten we kijken naar 
de event-storm en dan naar de User.

Laten we beginnen met een Aggregate.

<section>
<pre><code data-trim data-noescape>

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
</section>

<section>
<pre><code data-trim data-noescape>

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler //highlight 1
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING) //highlight 2 
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException { //highlight 3
            //do something useful
        }
    }
    
</code></pre>
</section>

<section>
<pre><code data-trim data-noescape>

    @EqualsAndHashCode
    public class Command {
        @Getter(onMethod_ = {@TargetAggregateIdentifier}) //highlight
        private String id;

        public Command() {

        }

        public Command(String id) {
            this.id = id;
        }
    }
    
</code></pre>
</section>

<section>
<pre><code data-trim data-noescape>

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
</section>

<section>
<pre><code data-trim data-noescape>

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler //highlight 1
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING) //highlight 2 
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException { //highlight 3
            if (id != null) {
                throw new PolicyViolatedException("Email already exists");
            }

        }
    }
    
</code></pre>
</section>

<section>
<pre><code data-trim data-noescape>

    @Aggregate
    public class UserAggregate {

        .......

        @CommandHandler //highlight 1
        @CreationPolicy(value = AggregateCreationPolicy.CREATE_IF_MISSING) //highlight 2 
        public void handle(RegisterUserCommand registerUserCommand) throws PolicyViolatedException { //highlight 3
            if (id != null) {
                throw new PolicyViolatedException("Email already exists");
            }

            final UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(registerUserCommand.getId(), registerUserCommand.getUser()); //highlight 1

            apply(userRegisteredEvent); highlight 2
        }
    }
    
</code></pre>
</section>

<section>
<pre><code data-trim data-noescape>

    @Getter
    @ToString
    @EqualsAndHashCode
    public abstract class Event {
        @Getter
        @TargetAggregateIdentifier //highlight 1
        private final String id;

        Event() {
            this.id = UUID.randomUUID().toString();
        }

        Event(String id) {
            this.id = id;
        }
    }
    
</code></pre>
</section>

<section>
<pre><code data-trim data-noescape>

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
</section>


<section>
<pre><code data-trim data-noescape>

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
</section>


<section>
<pre><code data-trim data-noescape>

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
</section>

<section>
<pre><code data-trim data-noescape>

    @Test
    public void testRegisterNewUser() {
        fixture.given(new UserRegisteredEvent(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .when(new RegisterUserCommand(UUID.nameUUIDFromBytes(user.getEmail().getBytes(StandardCharsets.UTF_8)).toString(), user))
                .expectException(PolicyViolatedException.class)
                .expectExceptionMessage("Email already exists");
    }
    
</code></pre>
</section>
Voorbeeld in de chess app -> User aggregate



Note:

- pre-defined event-storm presenteren + uitwerken
- demo eerste stuk van de event-storm (command)
- begin met een aggregate
- hoe wordt een aggregate getriggered? -> CommandHandler
- CommandHandler -> logic + Event
- Events definieren
- Event publishen
- Event Sourcing
- Hoe test je dit? -> TestFixture
- Debug statements toevoegen om het proces te volgen
- endpoints
- thoughts so far?

Doe het zelf in Matchmaker
