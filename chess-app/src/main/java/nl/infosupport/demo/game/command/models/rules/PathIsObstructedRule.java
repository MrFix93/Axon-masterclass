package nl.infosupport.demo.game.command.models.rules;

import nl.infosupport.demo.game.command.models.Board;
import nl.infosupport.demo.game.command.models.Move;
import nl.infosupport.demo.game.command.models.Piece;

public class PathIsObstructedRule implements ChessMoveRule {
    @Override
    public boolean isValid(Move move, Board board) {
        final Piece piece = move.getPiece();

        return piece.canJumpOverPiece() || !pathIsObstructed(move, board);
    }

    private boolean pathIsObstructed(Move move, Board board) {
        return move.getPath().stream().anyMatch(square -> board.getPieceBySquare(square).isPresent());
    }

    @Override
    public String getFailureReason() {
        return "Path is obstructed";
    }
}
