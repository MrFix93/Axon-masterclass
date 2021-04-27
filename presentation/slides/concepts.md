# Architectural Patterns

Note:
Om de mogelijkheden van Axon goed te kunnen begrijpen introduceren we eerste een aantal patronen die we terug gaan zien.

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/ddd.jpg)

Note:
Sommigen van jullie herkennen dit boek, dit is het boek van Eric Evans over Domain Driven Design.
Inmiddels is dit boek erg populair en wordt ook wel de bijbel van DDD genoemd.

--

> Domain-Driven Design is an approach to software development that centers the development on programming a domain model that has a rich understanding of the processes and rules of a domain.

by Martin Fowler

Note:
Martin Fowler schrijft over DDD Het volgende (citeer).
Bij DDD is de representatie van het domain model in code het allerbelangrijkste.

--

<!-- .slide: data-background-color="white" -->

![ddd-map](slides/images/ddd-map.png)

Note:
In het boek van Eric Evens worden hiervoor design patterns behandelt om je domein modellen zo goed mogelijk in code te kunen vatten. In deze cursus gaan we een aantal van dit soort patterns terug zien.

--

- Entity
- Value object
- Aggregate
- Domain Event
- Services
- Repository
- Factory

Note:
Van de concepten die we hebben gezien gaan we deze conceptern terug zien in Axon

--

## CQRS

CQRS = Command Query Responsibility Segragation

Note:
Een ander concept dat goed past binnen Axon is CQRS.
CQRS staat voor Command Query Responsibility Segragation en definieert eigenlijk 2 verschillende modellen voor het ophalen van informatie (Query) als het wijzigen van de state.

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/single-model.png)

Source: Martin Fowler

Note:
Hier zien we een klassieke aanpak met een UI, application en een database. We zien dat de UI informatie ophaalt uit de applicatie en informatie kan sturen naar de applicatie.
Bij het wijzigen van informatie wordt er informatie uit de database gehaald, aangepast en weer opgeslagen.
Bij het ophalen van informatie leest de informatie uit de database en wordt deze getoond aan de UI.
Dit model is erg regide. Want het data model in de database is meestal gefit op hoe de data opgehaald en opgeslagen moet worden.
Wanneer applicaties ingewikkelder wordt, kan dit regide model meer complexiteit opleveren.
Dan werkt het erg goed om deze twee modellen te scheiden.

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/cqrs.png)

Source: Martin Fowler

Note:
Door het scheiden van de modellen heb je eigenlijk 2 modellen, een query model en een command model die afzonderlijk van elkaar kunnen opereren.
Dat kan op dezelfde database, maar dat hoeft niet perse.
Omdat deze gescheiden zijn, en dat gaan we in axon ook zien, ook op verschillende databases opereren.
In theory zou je door deze scheiden beide modellen niet eens meer in dezelfde container of applicatie hoeven te definieren. Je zou 2 verschillende containers kunnen starten die je afzonderlijk kunt ontwikkelen.

--

## Event-sourcing

<p class="fragment">The fundamental idea of Event Sourcing is that of ensuring every change to the state of an application is captured in an event object,<p> <p class="fragment">and that these event objects are themselves stored in the sequence they were applied for the same lifetime as the application state itself.</p>

--

<!-- .slide: data-background-color="white" -->

![alt text](slides/images/event-sourcing.svg)

Note:
Door het gebruik van Event-sourcing definieer je eigenlijk elke actie in het systeem als een Event.
Een event kan een simpele POJO zijn.

--

## Advantages

<li class="fragment">Complete Rebuild</li>
<li class="fragment">Temporal Query</li>
<li class="fragment">Event Replay</li>
<li class="fragment">Audit log</li>

--

## Putting it all together

![alt text](slides/images/es-cqrs.jpg)
