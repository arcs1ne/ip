public enum Command {
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    BYE("bye");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    public static Command fromInput(String input) throws TangentException {
        for (Command command: Command.values()) {
            if (command.keyword.equals(input)) {
                return command;
            }
        }
        throw new TangentException("invalid command!");
    }
}