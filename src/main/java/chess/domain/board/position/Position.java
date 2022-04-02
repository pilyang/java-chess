package chess.domain.board.position;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Position {

    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final String RANK_FILE_DELIMITER = "";

    private static final List<Position> ALL_POSITIONS;

    static {
        ALL_POSITIONS = Arrays.stream(File.values())
                .flatMap(Position::generatePositionOf)
                .collect(Collectors.toUnmodifiableList());
    }

    private final File file;
    private final Rank rank;

    private Position(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    private static Stream<Position> generatePositionOf(final File file) {
        return Arrays.stream(Rank.values())
                .map(rank -> new Position(file, rank));
    }

    public static Position of(final File file, final Rank rank) {
        return ALL_POSITIONS.stream()
                .filter(position -> position.file == file)
                .filter(position -> position.rank == rank)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("위치 정보가 유효하지 않습니다."));
    }

    public static Position from(final String positionValue) {
        validatePositionValue(positionValue);

        final String[] rankAndFile = positionValue.split(RANK_FILE_DELIMITER);
        final String rank = rankAndFile[RANK_INDEX];
        final String file = rankAndFile[FILE_INDEX];

        return of(File.from(file), Rank.from(rank));
    }

    private static void validatePositionValue(final String positionValue) {
        if (positionValue.length() != 2) {
            throw new IllegalArgumentException("위치 정보가 유효하지 않습니다.");
        }
    }

    public void validateTargetPosition(final Position targetPosition,
                                       final BiPredicate<Integer, Integer> movingCondition) {
        final int differenceOfFile = this.file.calculateDifference(targetPosition.file);
        final int differenceOfRank = this.rank.calculateDifference(targetPosition.rank);

        if (!movingCondition.test(differenceOfFile, differenceOfRank)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    public void checkOtherPiecesInTarget(final Position targetPosition, final List<Position> otherPositions) {
        if (otherPositions.stream()
                .anyMatch(other -> other == targetPosition)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    public void checkOtherPiecesInPathToTarget(final Position targetPosition, final List<Position> positions) {
        Position currentPosition = this;
        while (currentPosition != targetPosition) {
            currentPosition.checkOtherPiecesInCurrentPosition(positions);
            currentPosition = currentPosition.nextPosition(targetPosition);
        }
    }

    private void checkOtherPiecesInCurrentPosition(final List<Position> positions) {
        if (positions.stream()
                .anyMatch(another -> this == another)) {
            throw new IllegalArgumentException("이동 경로에 다른 기물이 존재합니다.");
        }
    }

    private Position nextPosition(final Position targetPosition) {
        File nextFile = file.next(targetPosition.file);
        Rank nextRank = rank.next(targetPosition.rank);
        return Position.of(nextFile, nextRank);
    }

    public boolean isInFile(final File file) {
        return this.file == file;
    }

    public boolean isInRank(final Rank rank) {
        return this.rank == rank;
    }
}