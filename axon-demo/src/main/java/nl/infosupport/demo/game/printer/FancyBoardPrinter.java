package nl.infosupport.demo.game.printer;

import nl.infosupport.demo.game.exceptions.UnexpectedChessException;
import nl.infosupport.demo.game.models.*;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * ╔═══╤═══╤═══╤═══╤═══╤═══╤═══╤═══╗┈╮
 * ║ ♜ │ ♞ │ ♝ │ ♛ │ ♚ │ ♝ │ ♞ │ ♜ ║ 8
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║ ♟ │ ♟ │ ♟ │ ♟ │ ♟ │ ♟ │ ♟ │ ♟ ║ 7
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║ ░ │   │ ░ │   │ ░ │   │ ░ │   ║ 6
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║   │ ░ │   │ ░ │   │ ░ │   │ ░ ║ 5
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║ ░ │   │ ░ │   │ ░ │   │ ░ │   ║ 4
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║   │ ░ │   │ ░ │   │ ░ │   │ ░ ║ 3
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║ ♙ │ ♙ │ ♙ │ ♙ │ ♙ │ ♙ │ ♙ │ ♙ ║ 2
 * ╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊
 * ║ ♖ │ ♘ │ ♗ │ ♕ │ ♔ │ ♗ │ ♘ │ ♖ ║ 1
 * ╚═══╧═══╧═══╧═══╧═══╧═══╧═══╧═══╝ ┊
 * ╰┈a┈┈┈b┈┈┈c┈┈┈d┈┈┈e┈┈┈f┈┈┈g┈┈┈h┈┈┈╯
 */
public class FancyBoardPrinter implements ConsoleBoardPrinter {


    private static final String TOPP_LINE = "╔═══╤═══╤═══╤═══╤═══╤═══╤═══╤═══╗┈╮\n";
    private static final String SEPARATOR = "╟───┼───┼───┼───┼───┼───┼───┼───╢ ┊\n";
    private static final String BOTT_LINE = "╚═══╧═══╧═══╧═══╧═══╧═══╧═══╧═══╝ ┊\n";

    private static final String RANKS_FORMAT = "║ %s │ %s │ %s │ %s │ %s │ %s │ %s │ %s ║ %s\n";
    private static final String FILES_FORMAT = "╰┈%s┈┈┈%s┈┈┈%s┈┈┈%s┈┈┈%s┈┈┈%s┈┈┈%s┈┈┈%s┈┈┈╯\n";

    private final PrintStream printStream;
    private final PiecePrinter<String> piecePrinter;

    public FancyBoardPrinter(PrintStream printStream) {
        this.printStream = printStream;
        this.piecePrinter = new UnicodePiecePrinter();
    }

    @Override
    public void print(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        final List<Rank> ranks = new ArrayList<>(List.of(Rank.one, Rank.two, Rank.three, Rank.four, Rank.five, Rank.six, Rank.seven, Rank.eight));
        Collections.sort(ranks);
        Collections.reverse(ranks);

        stringBuilder.append(TOPP_LINE);
        for (Rank rank : ranks) {
            stringBuilder.append(print(rank, board));

            if (rank != Rank.one) {
                stringBuilder.append(SEPARATOR);
            }
        }
        stringBuilder.append(BOTT_LINE);

        final EnumSet<File> files = EnumSet.range(File.min(), File.max());
        final List<String> stringValues = new ArrayList<>();
        for (File file : files) {
            stringValues.add(file.name());
        }
        stringBuilder.append(String.format(FILES_FORMAT, stringValues.toArray()));

        final String boardString = stringBuilder.toString();

        PrintWriter printWriter = new PrintWriter(printStream, true, StandardCharsets.UTF_8);
        printWriter.println(boardString);
    }

    private String print(Rank rank, Board board) {
        final EnumSet<File> files = EnumSet.range(File.min(), File.max());

        final List<String> stringValues = new ArrayList<>();
        for (File file : files) {
            Square square = new Square(file, rank);
            Optional<Piece> piece = board.getKingOfColor(square);
            if (piece.isPresent()) {
                stringValues.add(fromCodetoString(piecePrinter.print(piece.get())));
            } else {
                stringValues.add(square.getColor() == ChessColor.BLACK ? "░" : " ");
            }
        }
        stringValues.add(rank.toString());

        return String.format(RANKS_FORMAT, stringValues.toArray());
    }

    private String fromCodetoString(String unicode) {
        Charset ascii = StandardCharsets.UTF_8;
        Charset def = Charset.defaultCharset();

        byte[] bytes = unicode.getBytes(ascii);

        try {
            return new String(bytes, def.name());
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedChessException("Unabled to print unicode representation in unicode", e);
        }
    }


}
