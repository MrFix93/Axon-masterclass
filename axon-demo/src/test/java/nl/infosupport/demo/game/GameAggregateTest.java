package nl.infosupport.demo.game;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameAggregateTest {

    private AggregateTestFixture<GameAggregate> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(GameAggregate.class);
    }
}
