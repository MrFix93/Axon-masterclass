# Axon server

Notes:

Normaal gesproken heb je in productie een aparte database voor de Command gedeelte
en Query gedeeltes. En heb je ook bijvoorbeeld een RabbitMQ nodig voor de 
Events. 

Dit zou ik zeker doen voor alle omgevingen als dat is geregeld. Maar als je even snel wilt 
kijken hoe het precies werkt in een lokale omgeving dan kan je gebruik maken 
van Axon server.

Dit regelt de communicatie van Events/messages en de storage ervan (event store).
Dit kan je starten om te kijken of alles naar verwachting werkt.

--

<pre><code class="bash">

  docker pull axoniq/axonserver
    
  docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver

</code></pre>

--

# Demo
