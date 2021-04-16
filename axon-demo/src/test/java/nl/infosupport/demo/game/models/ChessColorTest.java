package nl.infosupport.demo.game.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChessColorTest {

    @Test
    void testInvertWhite() {
        ChessColor white = ChessColor.WHITE;

        assertThat(white.invert()).isEqualByComparingTo(ChessColor.BLACK);
    }

    @Test
    void testInvertBlack() {
        ChessColor black = ChessColor.BLACK;

        assertThat(black.invert()).isEqualByComparingTo(ChessColor.WHITE);
    }
}
