<!-- .slide: data-background-image="https://media.giphy.com/media/3ohuAxV0DfcLTxVh6w/giphy.gif" -->

# Sagas

--

A Saga is a special type of event listener: one that manages a business transaction. Some transactions could be running for days or even weeks, while others are completed within a few milliseconds.

Note:
In een verspreid landschap met verschillende microservices zijn er middelen nodig om processen te kunnen coordineren. Om verschillende processen te coordineren kun je Saga's gebruiken. Een Saga is een gepersisteerde event handler met state. Een doorlooptijd van een Saga kan soms meteen zijn, maar kan ook langer dan een week, of in ons geval een paar maanden zijn. Een Saga maakt het ook mogelijk om Events 'in te plannen' met bijvoorbeeld Quartz.

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/saga-event-storm.jpg)

Note:

In ons voorbeeld kunnen we een saga defineren om het Game Time Over 'Event' in te plannen.

--

```java
@Saga
class GameTimerSaga {

    EventScheduler eventScheduler;

    @StartSaga
    @SagaEventHandler(associationProperty = "gameId")
    public void startSaga(GameStarted event) {
        // Example code
        eventScheduler.schedule(new GameTimeOver(gameId));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "gameId")
    public void endSaga(GameTimeOver event) {
        // ...
    }

}
```

Note:
Hoe doen we dat dan in Axon? Door de annotatie @Saga en met '@SagaEventHandlers'.
Met EventScheduler kunnen we event inplannen. Daarbij kunnen we dus met Spring Configuratie kiezen voor een Quartz variant.
