package chess.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static chess.domain.Rank.FIVE;
import static chess.domain.Rank.FOUR;
import static chess.domain.Rank.SEVEN;
import static chess.domain.Rank.SIX;
import static chess.domain.Rank.THREE;
import static chess.domain.Rank.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @ParameterizedTest
    @CsvSource({"ONE, 3", "TWO, 2", "FOUR, 0", "EIGHT, 4"})
    @DisplayName("거리 계산 테스트")
    void calculateDistanceTest(final Rank otherRank, final int expectedDistance) {
        final Rank rank = FOUR;

        final int actualDistance = rank.calculateDistance(otherRank);

        assertThat(actualDistance).isEqualTo(expectedDistance);
    }

    @Nested
    @DisplayName("두 파일 사이의 파일들을 구한다")
    class GetRanksToTest {

        @Test
        @DisplayName("오름차순 파일들을 반환한다")
        void get_ascending_test() {
            final Rank startRank = TWO;
            final Rank endRank = SEVEN;

            final List<Rank> betweenRanks = startRank.getRanksTo(endRank);

            assertThat(betweenRanks).containsExactly(THREE, FOUR, FIVE, SIX);
        }

        @Test
        @DisplayName("내림차순 파일들을 반환한다")
        void get_descending_test() {
            final Rank startRank = SEVEN;
            final Rank endRank = TWO;

            final List<Rank> betweenRanks = startRank.getRanksTo(endRank);

            assertThat(betweenRanks).containsExactly(SIX, FIVE, FOUR, THREE);
        }
    }
}