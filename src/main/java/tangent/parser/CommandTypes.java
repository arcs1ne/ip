package tangent.parser;

import tangent.exception.TangentException;

public enum CommandTypes {
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    BYE("bye");

    private final String keyword;

    CommandTypes(String keyword) {
        this.keyword = keyword;
    }

    public static CommandTypes fromInput(String input) throws TangentException {
        for (CommandTypes commandTypes : CommandTypes.values()) {
            if (commandTypes.keyword.equals(input)) {
                return commandTypes;
            }
        }
        throw new TangentException("invalid command!");
    }
}