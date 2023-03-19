package chess.domain.piece;

import chess.domain.piece.property.Color;
import chess.domain.position.Position;

import java.util.List;

public class Rook extends Piece {

    public Rook(final Position position, final Color color) {
        super(position, color);
    }

    @Override
    protected boolean canMove(final Position targetPosition) {
        return position.isInCrossPosition(targetPosition);
    }

    @Override
    public Piece move(final Piece pieceInTargetPosition) {
        final Position targetPosition = pieceInTargetPosition.getPosition();
        validateDestination(targetPosition);
        validateCatchingSameColor(pieceInTargetPosition);
        return new Rook(targetPosition, color);
    }

    @Override
    public List<Position> getPassingPositions(final Position targetPosition) {
        validateSamePosition(targetPosition);
        validateDestination(targetPosition);
        return position.findPassingPositions(targetPosition);
    }
}
