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
