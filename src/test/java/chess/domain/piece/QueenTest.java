package chess.domain.piece;

import chess.domain.position.File;
import chess.domain.position.Path;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.exception.ExceptionCode;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static chess.PositionFixture.A5;
import static chess.PositionFixture.B6;
import static chess.PositionFixture.C7;
import static chess.PositionFixture.D6;
import static chess.PositionFixture.D8;
import static chess.domain.piece.property.Color.BLACK;
import static chess.domain.piece.property.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueenTest {

    @Test
    @DisplayName("지나갈 경로를 얻는다.")
    void get_passing_path_test() {
        final Piece queen = new Queen(D8, BLACK);

        final Path path = queen.getPassingPositions(A5);

        assertThat(path).extracting("positions", InstanceOfAssertFactories.list(Position.class))
            .containsExactly(C7, B6);
    }


    @ParameterizedTest
    @CsvSource({"D, EIGHT", "E, FOUR"})
    @DisplayName("이동할 수 없는 위치가 입력되면, 예외가 발생한다.")
    void invalid_target_position_throw_exception(final File file, final Rank rank) {
        final Piece queen = new Queen(D8, BLACK);

        assertThatThrownBy(() -> queen.getPassingPositions(Position.of(file, rank)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionCode.INVALID_DESTINATION.name());
    }

    @ParameterizedTest
    @MethodSource("providePieceInTargetPosition")
    @DisplayName("말을 이동시킨다.")
    void move_test(final Piece pieceInTargetPosition) {
        final Piece originalQueen = new Queen(D8, BLACK);

        final Piece movedQueen = originalQueen.move(pieceInTargetPosition);

        assertThat(movedQueen.getPosition()).isEqualTo(pieceInTargetPosition.getPosition());
    }

    private static Stream<Arguments> providePieceInTargetPosition() {
        return Stream.of(
                Arguments.of(BlankPiece.of(D6)),
                Arguments.of(new Pawn(D6, WHITE))
        );
    }

    @Test
    @DisplayName("목표 위치에 같은 색 말이 있다면, 예외가 발생한다")
    void catch_same_color_throw_exception() {
        final Piece originalQueen = new Queen(D8, BLACK);
        final Piece sameColorPiece = new Pawn(D6, BLACK);

        assertThatThrownBy(() -> originalQueen.move(sameColorPiece))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionCode.TARGET_IS_SAME_COLOR.name());
    }
}
