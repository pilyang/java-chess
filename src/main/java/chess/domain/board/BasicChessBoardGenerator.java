package chess.domain.board;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;

public class BasicChessBoardGenerator {

    public static Board generator() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(Position.of("a8"), new Rook(Color.BLACK));
        board.put(Position.of("b8"), new Knight(Color.BLACK));
        board.put(Position.of("c8"), new Bishop(Color.BLACK));
        board.put(Position.of("d8"), new Queen(Color.BLACK));
        board.put(Position.of("e8"), new King(Color.BLACK));
        board.put(Position.of("f8"), new Bishop(Color.BLACK));
        board.put(Position.of("g8"), new Knight(Color.BLACK));
        board.put(Position.of("h8"), new Rook(Color.BLACK));

        board.put(Position.of("a7"), Pawn.of(Color.BLACK));
        board.put(Position.of("b7"), Pawn.of(Color.BLACK));
        board.put(Position.of("c7"), Pawn.of(Color.BLACK));
        board.put(Position.of("d7"), Pawn.of(Color.BLACK));
        board.put(Position.of("e7"), Pawn.of(Color.BLACK));
        board.put(Position.of("f7"), Pawn.of(Color.BLACK));
        board.put(Position.of("g7"), Pawn.of(Color.BLACK));
        board.put(Position.of("h7"), Pawn.of(Color.BLACK));

        board.put(Position.of("a1"), new Rook(Color.WHITE));
        board.put(Position.of("b1"), new Knight(Color.WHITE));
        board.put(Position.of("c1"), new Bishop(Color.WHITE));
        board.put(Position.of("d1"), new Queen(Color.WHITE));
        board.put(Position.of("e1"), new King(Color.WHITE));
        board.put(Position.of("f1"), new Bishop(Color.WHITE));
        board.put(Position.of("g1"), new Knight(Color.WHITE));
        board.put(Position.of("h1"), new Rook(Color.WHITE));

        board.put(Position.of("a2"), Pawn.of(Color.WHITE));
        board.put(Position.of("b2"), Pawn.of(Color.WHITE));
        board.put(Position.of("c2"), Pawn.of(Color.WHITE));
        board.put(Position.of("d2"), Pawn.of(Color.WHITE));
        board.put(Position.of("e2"), Pawn.of(Color.WHITE));
        board.put(Position.of("f2"), Pawn.of(Color.WHITE));
        board.put(Position.of("g2"), Pawn.of(Color.WHITE));
        board.put(Position.of("h2"), Pawn.of(Color.WHITE));

        return new Board(board);
    }
}