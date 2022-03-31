package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @DisplayName("범위 내 포지션을 입력하면 정상적으로 객체가 생성된다.")
    @ParameterizedTest(name = "{displayName} : {arguments}")
    @ValueSource(strings = {"a1", "a8", "h1", "h8"})
    void testCreatePosition(String input) {
        assertThatCode(() -> Position.of(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("범위 밖의 포지션을 입력하면 에러가 발생한다.")
    @Test
    void testFail() {
        assertThatThrownBy(() -> Position.of("a0"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 값의 Column과 Row를 가진 Position은 같은 Position이다")
    @Test
    void testSamePosition() {
        Position position1 = Position.of("a1");
        Position position2 = Position.of("a1");

        assertThat(position1).isEqualTo(position2);
    }

    @DisplayName("출발 위치와 도착 위치가 직선상에 존재하면 true를 반환한다 (북)")
    @Test
    void testCanCrossMovingStraightNorth() {
        Position source = Position.of("d3");
        Position destination = Position.of("d5");

        assertThat(source.canCrossMovingStraight(Direction.NORTH, destination)).isTrue();
    }

    @DisplayName("출발 위치와 도착 위치가 직선상에 존재하면 true를 반환한다 (북동)")
    @Test
    void testCanCrossMovingStraightCross() {
        Position source = Position.of("d5");
        Position destination = Position.of("g8");

        assertThat(source.canCrossMovingStraight(Direction.NORTH_EAST, destination)).isTrue();
    }

    @DisplayName("출발 위치와 도착 위치가 직선상에 존재하지 않으면 false를 반환한다")
    @Test
    void testCanCrossMovingStraight() {
        Position source = Position.of("d3");
        Position destination = Position.of("d1");

        assertThat(source.canCrossMovingStraight(Direction.NORTH, destination)).isFalse();
    }

    @DisplayName("파라미터로 넘어온 Row와 같은지 확인한다")
    @Test
    void testIsStartRow() {
        Position source = Position.of("a2");
        assertThat(source.isSameRank(Rank.RANK_2)).isTrue();
    }

    @DisplayName("Position은 캐싱되어 사용한다.")
    @Test
    void testCache() {
        Position position1 = Position.of("a2");
        Position position2 = Position.of("a2");

        assertThat(position1 == position2).isTrue();
    }
}