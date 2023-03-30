package chess.domain.board;

import chess.domain.piece.BlankPiece;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.maker.PiecesGenerator;
import chess.domain.piece.property.Color;
import chess.domain.position.Path;
import chess.domain.position.Position;
import chess.exception.ChessException;
import chess.exception.ExceptionCode;

import java.util.Optional;
import java.util.Set;

public class Board {

    private final Set<Piece> existingPieces;

    private Board(final Set<Piece> pieces) {
        this.existingPieces = pieces;
    }

    public static Board createWith(final PiecesGenerator piecesGenerator) {
        final Set<Piece> generatedPieces = piecesGenerator.generate();
        return new Board(generatedPieces);
    }

    public void move(final Position currentPosition, final Position targetPosition) {
        final Piece pieceToMove = findPieceOrElseThrowIn(currentPosition);
        final Path passingPositions = pieceToMove.getPassingPositions(targetPosition);

        validatePieceExistenceIn(passingPositions);

        processMoving(pieceToMove, targetPosition);
    }

    private Piece findPieceOrElseThrowIn(final Position position) {
        return findPieceOptionalIn(position)
                .orElseThrow(() -> new ChessException(ExceptionCode.PIECE_CAN_NOT_FOUND));
    }

    private Optional<Piece> findPieceOptionalIn(final Position position) {
        return existingPieces.stream()
                .filter(piece -> position.equals(piece.getPosition()))
                .findAny();
    }

    private void validatePieceExistenceIn(final Path passingPositions) {
        final boolean isBlocked = existingPieces.stream()
                .anyMatch(piece -> passingPositions.contains(piece.getPosition()));
        if (isBlocked) {
            throw new ChessException(ExceptionCode.PIECE_MOVING_PATH_BLOCKED);
        }
    }

    private void processMoving(final Piece pieceToMove, final Position targetPosition) {
        final Piece pieceInTargetPosition = findPieceOptionalIn(targetPosition)
                .orElseGet(() -> BlankPiece.of(targetPosition));
        final Piece movedPiece = pieceToMove.move(pieceInTargetPosition);

        existingPieces.remove(pieceToMove);
        existingPieces.remove(pieceInTargetPosition);
        existingPieces.add(movedPiece);
    }

    public boolean isSameColor(final Position position, final Color otherColor) {
        return findPieceOrElseThrowIn(position).isSameColor(otherColor);
    }

    public Set<Piece> getExistingPieces() {
        return Set.copyOf(existingPieces);
    }

    public boolean isKingExist(final Color color) {
        return existingPieces.stream()
                .anyMatch(piece -> piece.isSameColor(color) && PieceType.findByPiece(piece).equals(PieceType.KING));
    }
}
