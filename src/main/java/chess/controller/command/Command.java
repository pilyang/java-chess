package chess.controller.command;

import java.util.ArrayList;
import java.util.List;

public class Command {

    private static final String INVALID_PARAMETER_NUMBER_MESSAGE = "커멘드에 맞는 파라미터를 입력해주세요.";
    private static final int COMMAND_TYPE_INDEX = 0;
    private static final int COMMAND_PARAMETER_START_INDEX = 1;

    public static final int MOVE_CURRENT_POSITION_INDEX = 0;
    public static final int MOVE_TARGET_POSITION_INDEX = 1;

    private final Type type;
    private final List<String> parameters;

    private Command(final Type type, final List<String> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public static Command of(List<String> inputValues) {
        final Type commandType = Type.findBy(inputValues.get(COMMAND_TYPE_INDEX));
        final List<String> commandParameters = new ArrayList<>();
        validateParameterSize(inputValues, commandType);
        for (int index = COMMAND_PARAMETER_START_INDEX; index <= commandType.getRequiredParameterNumber(); index++) {
            commandParameters.add(inputValues.get(index));
        }
        return new Command(commandType, commandParameters);
    }

    private static void validateParameterSize(final List<String> inputValues, final Type commandType) {
        if (inputValues.size() - 1 != commandType.getRequiredParameterNumber()) {
            throw new IllegalArgumentException(INVALID_PARAMETER_NUMBER_MESSAGE);
        }
    }

    public boolean is(final Type compareType) {
        return type == compareType;
    }

    public String getParameterAt(final int parameterIndex) {
        return parameters.get(parameterIndex);
    }
}
