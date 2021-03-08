package nl.infosupport.demo.game.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SquareTest {

    @Test
    void testA1() {
        final Square square = new Square(File.a, 1);

        final Coordinate expected = new Coordinate(1, 1);
        final Coordinate actual = square.getCoordinates();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testB5() {
        final Square square = new Square(File.b, 5);

        final Coordinate expected = new Coordinate(2, 5);
        final Coordinate actual = square.getCoordinates();

        assertThat(actual).isEqualTo(expected);
    }
}
