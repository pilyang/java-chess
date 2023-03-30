package chess.controller.command;

import chess.domain.state.ChessState;
import chess.exception.ChessException;
import chess.exception.ExceptionCode;

import java.util.List;
import java.util.function.BiFunction;

public class CommandAction {

    public static final CommandAction INVALID_ACTION = new CommandAction((chessState, commandParameters) -> {
        throw new ChessException(ExceptionCode.INVALID_COMMAND);
    });

    private final BiFunction<ChessState, List<String>, ChessState> function;

    public CommandAction(final BiFunction<ChessState, List<String>, ChessState> function){
        this.function = function;
    }

    public ChessState run(final ChessState initialChessState, final List<String> commandParameters) {
        return function.apply(initialChessState, commandParameters);
    }
}
