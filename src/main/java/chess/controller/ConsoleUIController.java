package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandReader;
import chess.domain.gamestatus.GameStatus;
import chess.domain.gamestatus.NothingHappened;
import chess.domain.gamestatus.Running;
import chess.domain.score.Score;
import chess.view.InputView;
import chess.view.OutputView;

public class ConsoleUIController {

    public static void run() {
        try {
            runWithoutExceptionCatch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void runWithoutExceptionCatch() {
        GameStatus gameStatus = new NothingHappened();

        OutputView.printStartInformation();

        do {
            Command command = CommandReader.of(InputView.read());
            gameStatus = command.execute(gameStatus);
        } while (gameStatus instanceof Running);

        conclude(gameStatus.scoring());
    }

    private static void conclude(Score score) {
        if (score.isDraw()) {
            OutputView.printResult(score.getScores());
            return;
        }
        OutputView.printResult(score.getScores(), score.getWinner());
    }
}