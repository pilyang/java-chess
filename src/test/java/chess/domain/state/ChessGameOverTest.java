package chess.domain.state;

import chess.TestPiecesGenerator;
import chess.dao.InMemoryChessGameDao;
import chess.dao.InMemoryPieceDao;
import chess.domain.ChessGame;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.dto.domaintocontroller.GameStatus;
import chess.exception.ChessException;
import chess.exception.ExceptionCode;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static chess.PositionFixture.A1;
import static chess.PositionFixture.A3;
import static chess.PositionFixture.E1;
import static chess.PositionFixture.E8;
import static chess.domain.piece.property.Color.BLACK;
import static chess.domain.piece.property.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessGameOverTest {

    private static final int TEST_GAME_ROOM_ID = 1;

    @Test
    @DisplayName("게임 시작 명령시 새 게임을 생성한다")
    void start_new_game_test() {
        final ChessGameOver chessGameOver = new ChessGameOver(ChessGame.createWith(new TestPiecesGenerator(List.of(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE))),
                ChessGame.INITIAL_STARTING_COLOR,
                TEST_GAME_ROOM_ID
        ), new InMemoryChessGameDao(), new InMemoryPieceDao());

        final ChessState state = chessGameOver.start();

        assertThat(state).isInstanceOf(ChessRunning.class);
    }

    @Test
    @DisplayName("이동 명령시 예외를 발생한다")
    void move_chess_command_throw_exception() {
        final ChessGameOver chessGameOver = new ChessGameOver(ChessGame.createWith(new TestPiecesGenerator(List.of(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE))),
                ChessGame.INITIAL_STARTING_COLOR,
                TEST_GAME_ROOM_ID
        ), new InMemoryChessGameDao(), new InMemoryPieceDao());

        assertThatThrownBy(() -> chessGameOver.move(A3, A1))
                .isInstanceOf(ChessException.class)
                .hasMessage(ExceptionCode.GAME_OVER_STATE.name());
    }

    @Test
    @DisplayName("게임 종료 명령시 상태를 종료 변경한다")
    void end_chess_test() {
        final ChessGameOver chessGameOver = new ChessGameOver(ChessGame.createWith(new TestPiecesGenerator(List.of(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE))),
                ChessGame.INITIAL_STARTING_COLOR,
                TEST_GAME_ROOM_ID
        ), new InMemoryChessGameDao(), new InMemoryPieceDao());

        final ChessState state = chessGameOver.end();

        assertThat(state).isInstanceOf(ChessEnd.class);
    }

    @Test
    @DisplayName("승패 결과를 확인한다")
    void get_score_from_status_test() {
        final ChessGameOver chessGameOver = new ChessGameOver(ChessGame.createWith(new TestPiecesGenerator(List.of(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE))),
                ChessGame.INITIAL_STARTING_COLOR,
                TEST_GAME_ROOM_ID
        ), new InMemoryChessGameDao(), new InMemoryPieceDao());

        final GameStatus status = chessGameOver.status();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(status.getWinningTeamColor()).isEqualTo(BLACK);
            softly.assertThat(status.getBlackScore()).isEqualTo(0.0);
            softly.assertThat(status.getWhiteScore()).isEqualTo(0.0);
        });
    }

    @Test
    @DisplayName("체스말을 가져온다")
    void getting_existing_piece_test() {
        final ChessGameOver chessGameOver = new ChessGameOver(ChessGame.createWith(new TestPiecesGenerator(List.of(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE))),
                ChessGame.INITIAL_STARTING_COLOR,
                TEST_GAME_ROOM_ID
        ), new InMemoryChessGameDao(), new InMemoryPieceDao());

        final Set<Piece> existingPieces = chessGameOver.getExistingPieces();

        assertThat(existingPieces).containsExactlyInAnyOrder(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE)
        );
    }

    @Test
    @DisplayName("게임 종료 여부를 확인한다")
    void check_if_is_end_test() {
        final ChessGameOver chessGameOver = new ChessGameOver(ChessGame.createWith(new TestPiecesGenerator(List.of(
                new King(E1, BLACK),
                new Queen(E8, BLACK),
                new Rook(A3, WHITE))),
                ChessGame.INITIAL_STARTING_COLOR,
                TEST_GAME_ROOM_ID
        ), new InMemoryChessGameDao(), new InMemoryPieceDao());

        final boolean isEnd = chessGameOver.isEnd();

        assertThat(isEnd).isFalse();
    }

}
