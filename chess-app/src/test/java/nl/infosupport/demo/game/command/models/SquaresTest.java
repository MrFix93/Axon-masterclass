package nl.infosupport.demo.game.command.models;

import nl.infosupport.demo.game.command.utils.Squares;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SquaresTest {

    @Test
    void testPatternMatching() {
        final Square a4 = Squares.from("a4");

        assertThat(a4.getRank()).isEqualTo(Rank.four);
        assertThat(a4.getFile()).isEqualTo(File.a);
    }
}
