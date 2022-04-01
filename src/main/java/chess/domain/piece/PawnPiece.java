package chess.domain.piece;

import chess.domain.Direction;
import chess.domain.position.Position;
import chess.domain.position.Row;

public class PawnPiece extends Piece {

    private static final String NAME = "P";
    private static final double SCORE = 1.0;
    private static final int LIMIT_DISTANCE = 2;

    public PawnPiece(final Color color) {
        super(color, NAME);
    }

    @Override
    public boolean isRightMovement(final Position from, final Position to, final boolean isEmptyTarget) {
        final int columnDistance = to.calculateColumnDistance(from);
        final int rowDistance = to.calculateRowDistance(from);

        final Direction direction = Direction.of(columnDistance, rowDistance);

        return isInitialForwardMove(direction, isEmptyTarget, from, rowDistance) ||
                isForwardMove(direction, isEmptyTarget, rowDistance) ||
                isDiagonalMove(direction, isEmptyTarget, rowDistance, columnDistance);
    }

    private boolean isInitialForwardMove(final Direction direction, final boolean isEmptyTarget, final Position from,
                                         final int rowDistance) {
        return isEmptyTarget && ((super.isSameColor(Color.BLACK) && from.isSameRow(Row.SEVEN) &&
                direction == Direction.SOUTH && Math.abs(rowDistance) <= LIMIT_DISTANCE) ||
                (super.isSameColor(Color.WHITE) && from.isSameRow(Row.TWO) &&
                        direction == Direction.NORTH && Math.abs(rowDistance) <= LIMIT_DISTANCE));
    }

    private boolean isForwardMove(final Direction direction, final Boolean isEmptyTarget, final int rowDistance) {
        return isForward(direction) && isEmptyTarget && Math.abs(rowDistance) < LIMIT_DISTANCE;
    }

    private boolean isForward(final Direction direction) {
        return (super.isSameColor(Color.WHITE) && direction == Direction.NORTH) ||
                (super.isSameColor(Color.BLACK) && direction == Direction.SOUTH);
    }

    private boolean isDiagonalMove(final Direction direction, final boolean isEmptyTarget, final int rowDistance,
                                   final int fileDistance) {
        return isDiagonal(direction) && !isEmptyTarget && Math.abs(fileDistance) < LIMIT_DISTANCE
                && Math.abs(rowDistance) < LIMIT_DISTANCE;
    }

    private boolean isDiagonal(final Direction direction) {
        return (super.isSameColor(Color.WHITE) &&
                (direction == Direction.NORTH_EAST || direction == Direction.NORTH_WEST)) ||
                (super.isSameColor(Color.BLACK) &&
                        (direction == Direction.SOUTH_EAST || direction == Direction.SOUTH_WEST));
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public double getScore() {
        return SCORE;
    }
}